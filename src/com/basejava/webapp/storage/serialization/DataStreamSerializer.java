package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
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
                if (entry.getValue() instanceof SimpleTextContent) {
                    dos.writeUTF("SimpleTextContent");
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(r.getContent(entry.getKey()).toString());
                } else if (entry.getValue() instanceof StringListContent) {
                    dos.writeUTF("StringListContent");
                    writeStringListContent(dos, r, entry.getKey());
                } else if (entry.getValue() instanceof OrganizationContent) {
                    dos.writeUTF("OrganizationContent");
                    writeOrganization(dos, r, entry.getKey());
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
                String marker = dis.readUTF();
                switch (marker) {
                    case "SimpleTextContent" -> resume.setContent(SectionType.valueOf(dis.readUTF()), new SimpleTextContent(dis.readUTF()));
                    case "StringListContent" -> readStringListContent(dis, resume);
                    case "OrganizationContent" -> readOrganization(dis, resume);
                }
            }
            return resume;
        }
    }

    private void writeStringListContent(DataOutputStream dos, Resume resume, SectionType type) throws IOException {
        dos.writeUTF(type.toString());
        StringListContent list = (StringListContent) resume.getContent(type);
        String[] strings = list.getStringList().toArray(new String[0]);
        dos.writeInt(strings.length);
        for (String string : strings) {
            dos.writeUTF(string);
        }
    }

    private void readStringListContent(DataInputStream dis, Resume resume) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        String[] strings = new String[dis.readInt()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = dis.readUTF();
        }
        resume.setContent(sectionType, new StringListContent(Arrays.asList(strings)));
    }

    private void writeOrganization(DataOutputStream dos, Resume resume, SectionType type) throws IOException {
        dos.writeUTF(type.toString());
        OrganizationContent oList = (OrganizationContent) resume.getContent(type);
        Organization[] o = oList.getOrganizations().toArray(new Organization[0]);
        dos.writeInt(o.length);
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

    private void readOrganization(DataInputStream dis, Resume resume) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        Organization[] o = new Organization[dis.readInt()];
        for (int i = 0; i < o.length; i++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            if (url.equals("null")) {
                o[i] = new Organization(name, null, readPeriod(dis));
            } else {
                o[i] = new Organization(name, url, readPeriod(dis));
            }
        }
        resume.setContent(sectionType, new OrganizationContent(Arrays.asList(o)));
    }

    private void writePeriod(DataOutputStream dos, Organization o) throws IOException {
        Organization.Period[] periods = o.getPeriod().toArray(new Organization.Period[0]);
        dos.writeInt(periods.length);
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

    private Organization.Period[] readPeriod(DataInputStream dis) throws IOException {
        Organization.Period[] periods = new Organization.Period[dis.readInt()];
        for (int i = 0; i < periods.length; i++) {
            String description = dis.readUTF();
            if (description.equals("null")) {
                periods[i] = new Organization.Period(readDate(dis), readDate(dis), dis.readUTF(), null);
            } else {
                periods[i] = new Organization.Period(readDate(dis), readDate(dis), dis.readUTF(), description);
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
