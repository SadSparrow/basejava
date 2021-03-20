package com.basejava.webapp.model;

public class SimpleTextContent implements Content {
    private String simpleText;

    public SimpleTextContent() {
        simpleText = "simpleText - поле не заполнено";
    }

    public SimpleTextContent(String simpleText) {
        this.simpleText = simpleText;
    }

    public String getSimpleText() {
        return simpleText;
    }

    public void SimpleTextContent(String simpleText) {
        this.simpleText = simpleText;
    }

    @Override
    public String toString() {
        return getSimpleText();
    }
}
