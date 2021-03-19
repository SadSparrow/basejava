package com.basejava.webapp.model;

public enum SectionType {
    OBJECTIVE("Позиция"), //строка
    PERSONAL("Личные качества"), //строка
    ACHIEVEMENT("Достижения"), //список строк
    QUALIFICATIONS("Квалификация"), //список строк
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
