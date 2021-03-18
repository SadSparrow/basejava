package com.basejava.webapp.model;

public enum ContactType {
    PHONE("Тел.: "),
    SKYPE("Skype: "),
    MAIl("Почта: ");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
