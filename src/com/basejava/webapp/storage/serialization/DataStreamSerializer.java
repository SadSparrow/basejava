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
                SectionType type = entry.getKey();
                dos.writeUTF(type.name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF((((SimpleTextContent) r.getContent(type)).getSimpleText()));
                    case ACHIEVEMENT, QUALIFICATIONS -> writeStringListContent(dos, ((StringListContent) r.getContent(type)).getStringList());
                    case EXPERIENCE, EDUCATION -> writeOrganization(dos, ((OrganizationContent) r.getContent(type)).getOrganizations());
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
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.setContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
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

    private void writeStringListContent(DataOutputStream dos, List<String> strings) throws IOException {
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

    private void writeOrganization(DataOutputStream dos, List<Organization> orgs) throws IOException {
        dos.writeInt(orgs.size());
        for (Organization org : orgs) {
            Link link = org.getHomePage();
            dos.writeUTF(link.getUrl() != null ? link.getUrl() : "null");
            dos.writeUTF(link.getName());
            writePeriod(dos, org);
        }
    }

    private List<Organization> readOrganization(DataInputStream dis) throws IOException {
        List<Organization> orgs = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            String url = dis.readUTF();
            Link homePage = new Link(dis.readUTF(), url.equals("null") ? null : url);
            orgs.add(new Organization(homePage, readPeriod(dis)));
        }
        return orgs;
    }

    private void writePeriod(DataOutputStream dos, Organization org) throws IOException {
        List<Organization.Period> periods = org.getPeriod();
        dos.writeInt(periods.size());
        for (Organization.Period period : periods) {
            dos.writeUTF(period.getDescription() != null ? period.getDescription() : "null");//description
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
            periods.add(new Organization.Period(readDate(dis), readDate(dis), dis.readUTF(), description.equals("null") ? null : description));
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
