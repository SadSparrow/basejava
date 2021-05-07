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
            MapWriter<ContactType, String> contactsInterface = (k, v) -> {
                dos.writeUTF(k.name());
                dos.writeUTF(v);
            };

            Map<SectionType, AbstractContent> content = r.getContents();
            MapWriter<SectionType, AbstractContent> contentInterface = (k, v) -> {
                dos.writeUTF(k.name());
                switch (k) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF((((SimpleTextContent) r.getContent(k)).getSimpleText()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> stringList = ((StringListContent) r.getContent(k)).getStringList();
                        ListWriter<String> stringListInterface = dos::writeUTF;
                        writeWithException(stringList, dos, stringListInterface);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> orgList = ((OrganizationContent) r.getContent(k)).getOrganizations();
                        ListWriter<Organization> orgListInterface = t -> {
                            Link link = t.getHomePage();
                            String url = link.getUrl();
                            dos.writeUTF(url != null ? url : "null");
                            dos.writeUTF(link.getName());
                            List<Organization.Period> periodsList = t.getPeriod();
                            ListWriter<Organization.Period> periodsListInterface = p -> {
                                String description = p.getDescription();
                                dos.writeUTF(description != null ? description : "null");
                                writeDate(dos, p.getStartDate()); //startDate
                                writeDate(dos, p.getEndDate()); //endDate
                                dos.writeUTF(p.getTitle()); //title
                            };
                            writeWithException(periodsList, dos, periodsListInterface);
                        };
                        writeWithException(orgList, dos, orgListInterface);
                    }
                }
            };
            writeWithException(contacts, dos, contactsInterface);
            writeWithException(content, dos, contentInterface);
        }
    }

    @FunctionalInterface
    private interface MapWriter<K, V> {
        void writeCollection(K k, V v) throws IOException;
    }

    @FunctionalInterface
    private interface ListWriter<T> {
        void writeCollection(T t) throws IOException;
    }

    private <K, V> void writeWithException(Map<K, V> map, DataOutputStream dos, MapWriter<K, V> myInterface) throws IOException {
        dos.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            myInterface.writeCollection(entry.getKey(), entry.getValue());
        }
    }

    private <T> void writeWithException(List<T> list, DataOutputStream dos, ListWriter<T> myInterface) throws IOException {
        dos.writeInt(list.size());
        for (T t : list) {
            myInterface.writeCollection(t);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            MapReader contactsInterface = c -> resume.setContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            readWithException(dis, contactsInterface);

            MapReader sectionInterface = s -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.setContent(type, new SimpleTextContent(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListReader<String> stringListInterface = DataInput::readUTF;
                        resume.setContent(type, new StringListContent(readWithException(dis, stringListInterface)));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        ListReader<Organization> orgListInterface = o -> {
                            String url = dis.readUTF();
                            Link homePage = new Link(dis.readUTF(), url.equals("null") ? null : url);
                            ListReader<Organization.Period> periodsListInterface = p -> {
                                String description = dis.readUTF();
                                return new Organization.Period(readDate(dis), readDate(dis), dis.readUTF(), description.equals("null") ? null : description);
                            };
                            return new Organization(homePage, readWithException(dis, periodsListInterface));
                        };
                        resume.setContent(type, new OrganizationContent(readWithException(dis, orgListInterface)));
                    }
                }
            };
            readWithException(dis, sectionInterface);
            return resume;
        }
    }

    @FunctionalInterface
    private interface MapReader {
        void readCollection(DataInputStream dis) throws IOException;
    }

    @FunctionalInterface
    private interface ListReader<T> {
        T readCollection(DataInputStream dis) throws IOException;
    }

    private <T> List<T> readWithException(DataInputStream dis, ListReader<T> myInterface) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(myInterface.readCollection(dis));
        }
        return list;
    }

    private void readWithException(DataInputStream dis, MapReader myInterface) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            myInterface.readCollection(dis);
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear()); //year
        dos.writeInt(date.getMonthValue()); //month
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
    }
}
