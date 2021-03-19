package com.basejava.webapp.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

import static com.basejava.webapp.model.ContactType.*;
import static com.basejava.webapp.model.SectionType.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private final EnumMap<ContactType, String> contacts;
    private final EnumMap<SectionType, Content> content;

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = new EnumMap<>(ContactType.class) {
            {
                put(PHONE, "не заполнено");
                put(SKYPE, "не заполнено");
                put(MAIl, "не заполнено");
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (ContactType c : ContactType.values()) {
                    sb.append(c.getTitle()).append(contacts.get(c)).append("\n");
                }
                return sb.toString();
            }
        };
        this.content = new EnumMap<>(SectionType.class) {
            {
                put(OBJECTIVE, new ContentSimpleText());
                put(PERSONAL, new ContentSimpleText());
                put(ACHIEVEMENT, new ContentStringList());
                put(QUALIFICATIONS, new ContentStringList());
                put(EXPERIENCE, new ContentDateIntervalText());
                put(EDUCATION, new ContentDateIntervalText());
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (SectionType s : SectionType.values()) {
                    sb.append(s.getTitle()).append("\n").append(content.get(s)).append("\n");
                }
                return sb.toString();
            }
        };
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public EnumMap<ContactType, String> getContacts() {
        return contacts;
    }

    public void setPhone(String phone) {
        contacts.replace(PHONE, phone);
    }

    public String getPhone() {
        return contacts.get(PHONE);
    }

    public void setSkype(String skype) {
        contacts.replace(SKYPE, skype);
    }

    public String getSkype() {
        return contacts.get(SKYPE);
    }

    public void setMail(String mail) {
        contacts.replace(MAIl, mail);
    }

    public String getMail() {
        return contacts.get(MAIl);
    }

    public void setPhoneSkypeMail(String phone, String skype, String mail) {
        contacts.replace(PHONE, phone);
        contacts.replace(SKYPE, skype);
        contacts.replace(MAIl, mail);
    }

    public EnumMap<SectionType, Content> getContent() {
        return content;
    }

    public String getObjective() {
        return content.get(OBJECTIVE).toString();
    }

    public void setObjective(String text) {
        content.replace(OBJECTIVE, new ContentSimpleText(text));
    }

    public String getPersonal() {
        return content.get(PERSONAL).toString();
    }

    public void setPersonal(String text) {
        content.replace(PERSONAL, new ContentSimpleText(text));
    }

    public String getAchievment() {
        return content.get(ACHIEVEMENT).toString();
    }

    public void setAchievement(List<String> stringList) {
        content.replace(ACHIEVEMENT, new ContentStringList(stringList));
    }

    public String getQualifications() {
        return content.get(QUALIFICATIONS).toString();
    }

    public void setQualifications(List<String> stringList) {
        content.replace(QUALIFICATIONS, new ContentStringList(stringList));
    }

    //EXPERIENCE Experience
    public String getExperience() {
        return content.get(EXPERIENCE).toString();
    }

    public void setExperience(ContentDateIntervalText c) {
        content.replace(EXPERIENCE, c);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "uuid: " + uuid + ", full name: " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}
