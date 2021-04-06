package com.basejava.webapp.model;

import java.util.Objects;

public class SimpleTextContent extends AbstractContent {
    private final String simpleText;

    public SimpleTextContent(String simpleText) {
        Objects.requireNonNull(simpleText, "simpleText must not be null");
        this.simpleText = simpleText;
    }

    public String getSimpleText() {
        return simpleText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTextContent that = (SimpleTextContent) o;

        return simpleText.equals(that.simpleText);
    }

    @Override
    public int hashCode() {
        return simpleText.hashCode();
    }

    @Override
    public String toString() {
        return simpleText;
    }
}
