package com.basejava.webapp.model;

public class ContentSimpleText implements Content {
    private String simpleText;

    public ContentSimpleText() {
        simpleText = "simpleText - поле не заполнено";
    }

    public ContentSimpleText(String simpleText) {
        this.simpleText = simpleText;
    }

    public String getSimpleText() {
        return simpleText;
    }

    public void setSimpleText(String simpleText) {
        this.simpleText = simpleText;
    }

    @Override
    public String toString() {
        return getSimpleText();
    }
}
