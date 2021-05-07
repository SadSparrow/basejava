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
            WriteMap<ContactType, String> contactsInterface = (k, v) -> {
                dos.writeUTF(k.name());
                dos.writeUTF(v);
            };

            Map<SectionType, AbstractContent> content = r.getContents();
            WriteMap<SectionType, AbstractContent> contentInterface = (k, v) -> {
                dos.writeUTF(k.name());
                switch (k) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF((((SimpleTextContent) r.getContent(k)).getSimpleText()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> stringList = ((StringListContent) r.getContent(k)).getStringList();
                        WriteList<String> stringListInterface = dos::writeUTF;
                        writeWithException(stringList, dos, stringListInterface);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> orgList = ((OrganizationContent) r.getContent(k)).getOrganizations();
                        WriteList<Organization> orgListInterface = t -> {
                            Link link = t.getHomePage();
                            String url = link.getUrl();
                            dos.writeUTF(url != null ? url : "null");
                            dos.writeUTF(link.getName());
                            List<Organization.Period> periodsList = t.getPeriod();
                            WriteList<Organization.Period> periodsListInterface = p -> {
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

    //как записывать каждый отдельный элемент коллекции
    @FunctionalInterface
    private interface WriteMap<K, V> {
        void writeCollection(K k, V v) throws IOException;
    }

    @FunctionalInterface
    private interface WriteList<T> {
        void writeCollection(T t) throws IOException;
    }

    //который как параметры принимает коллекцию (в буквальном смысле), DataOutputStream и твой функциональный интерфейс.
    private <K, V> void writeWithException(Map<K, V> map, DataOutputStream dos, WriteMap<K, V> myInterface) throws IOException {
        dos.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            myInterface.writeCollection(entry.getKey(), entry.getValue());
        }
    }

    private <T> void writeWithException(List<T> list, DataOutputStream dos, WriteList<T> myInterface) throws IOException {
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

            ReadMap contactsInterface = c -> resume.setContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            readWithException(dis, contactsInterface);

            ReadMap sectionInterface = s -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.setContent(type, new SimpleTextContent(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ReadList<String> stringListInterface = DataInput::readUTF;
                        resume.setContent(type, new StringListContent(readWithException(dis, stringListInterface)));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        ReadList<Organization> orgListInterface = o -> {
                            String url = dis.readUTF();
                            Link homePage = new Link(dis.readUTF(), url.equals("null") ? null : url);
                            ReadList<Organization.Period> periodsListInterface = p -> {
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
    private interface ReadMap {
        void readCollection(DataInputStream dis) throws IOException;
    }

    @FunctionalInterface
    private interface ReadList<T> {
        T readCollection(DataInputStream dis) throws IOException;
    }

    private <T> List<T> readWithException(DataInputStream dis, ReadList<T> myInterface) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(myInterface.readCollection(dis)); // это верно?
        }
        return list;
    }

    private void readWithException(DataInputStream dis, ReadMap myInterface) throws IOException {
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
