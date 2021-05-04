package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractContent> content = r.getContents();
            dos.writeInt(content.size());
            for (Map.Entry<SectionType, AbstractContent> entry : content.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(String.valueOf(r.getContent(entry.getKey())));
                    case ACHIEVEMENT, QUALIFICATIONS -> writeStringListContent(dos, r, entry.getKey());
                    case EXPERIENCE, EDUCATION -> writeOrganization(dos, r, entry.getKey());
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size1 = dis.readInt();
            for (int i = 0; i < size1; i++) {
                resume.setContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int size2 = dis.readInt();
            for (int i = 0; i < size2; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.setContent(type, new SimpleTextContent(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.setContent(type, new StringListContent(readStringListContent(dis)));
                    case EXPERIENCE, EDUCATION -> resume.setContent(type, new OrganizationContent(readOrganization(dis)));
                }
            }
            return resume;
        }
    }

    private void writeStringListContent(DataOutputStream dos, Resume resume, SectionType type) throws IOException {
        StringListContent list = (StringListContent) resume.getContent(type);
        List<String> strings = list.getStringList();
        dos.writeInt(strings.size());
        for (String string : strings) {
            dos.writeUTF(string);
        }
    }

    private List<String> readStringListContent(DataInputStream dis) throws IOException {
        List<String> strings = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            strings.add(dis.readUTF());
        }
        return strings;
    }

    private void writeOrganization(DataOutputStream dos, Resume resume, SectionType type) throws IOException {
        OrganizationContent oList = (OrganizationContent) resume.getContent(type);
        List<Organization> o = oList.getOrganizations();
        dos.writeInt(o.size());
        for (Organization organization : o) {
            dos.writeUTF(organization.getHomePage().getName()); //Link
            if (organization.getHomePage().getUrl() != null) {
                dos.writeUTF(organization.getHomePage().getUrl());
            } else {
                dos.writeUTF("null");
            }
            writePeriod(dos, organization);
        }
    }

    private List<Organization> readOrganization(DataInputStream dis) throws IOException {
        List<Organization> o = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            Link homePage = new Link(dis.readUTF(), dis.readUTF());
            if (homePage.getUrl().equals("null")) {
                homePage.setUrl(null);
            }
            o.add(new Organization(homePage, readPeriod(dis)));
        }
        return o;
    }

    private void writePeriod(DataOutputStream dos, Organization o) throws IOException {
        List<Organization.Period> periods = o.getPeriod();
        dos.writeInt(periods.size());
        for (Organization.Period period : periods) {
            if (period.getDescription() != null) {
                dos.writeUTF(period.getDescription()); //description
            } else {
                dos.writeUTF("null");
            }
            writeDate(dos, period.getStartDate()); //startDate
            writeDate(dos, period.getEndDate()); //endDate
            dos.writeUTF(period.getTitle()); //title
        }
    }

    private List<Organization.Period> readPeriod(DataInputStream dis) throws IOException {
        List<Organization.Period> periods = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            String description = dis.readUTF();
            if (description.equals("null")) {
                periods.add(new Organization.Period(readDate(dis), readDate(dis), dis.readUTF(), null));
            } else {
                periods.add(new Organization.Period(readDate(dis), readDate(dis), dis.readUTF(), description));
            }
        }
        return periods;
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear()); //year
        dos.writeInt(date.getMonthValue()); //month
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
    }
}
