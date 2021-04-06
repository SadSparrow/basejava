package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class StringListContent extends AbstractContent {
    private final List<String> stringList;

    public StringListContent(List<String> stringList) {
        Objects.requireNonNull(stringList, "stringList must not be null");
        this.stringList = stringList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringListContent that = (StringListContent) o;

        return stringList.equals(that.stringList);
    }

    @Override
    public int hashCode() {
        return stringList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : stringList) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
