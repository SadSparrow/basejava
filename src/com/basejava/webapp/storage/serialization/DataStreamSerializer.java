package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();

            WriteCollection<ContactType, String> contactsInterface = (k, v) -> {
                dos.writeUTF(k.name());
                dos.writeUTF(v);
            };

            Map<SectionType, AbstractContent> content = r.getContents();
            WriteCollection<SectionType, AbstractContent> contentInterface = (k, v) -> {
                dos.writeUTF(k.name());
                switch (k) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF((((SimpleTextContent) r.getContent(k)).getSimpleText()));
                    case ACHIEVEMENT, QUALIFICATIONS -> writeStringListContent(dos, ((StringListContent) r.getContent(k)).getStringList());
                    case EXPERIENCE, EDUCATION -> writeOrganization(dos, ((OrganizationContent) r.getContent(k)).getOrganizations());
                }
            };
            writeWithException(contacts, dos, contactsInterface);
            writeWithException(content, dos, contentInterface);
        }
    }

    //как записывать каждый отдельный элемент коллекции
    @FunctionalInterface
    private interface WriteCollection<K, V> {
        void writeCollection(K k, V v) throws IOException;

    }

    //который как параметры принимает коллекцию (в буквальном смысле), DataOutputStream и твой функциональный интерфейс.
    private <K, V> void writeWithException(Map<K, V> map, DataOutputStream dos, WriteCollection<K, V> myInterface) throws IOException {
        dos.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            myInterface.writeCollection(entry.getKey(), entry.getValue());
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
            String url = link.getUrl();
            dos.writeUTF(url != null ? url : "null");
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
            String description = period.getDescription();
            dos.writeUTF(description != null ? description : "null");
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
