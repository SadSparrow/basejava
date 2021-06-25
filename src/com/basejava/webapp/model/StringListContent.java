package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class StringListContent extends AbstractContent {
    private List<String> stringList;
    public static final StringListContent EMPTY = new StringListContent("");

    public StringListContent() {
    }

    public StringListContent(String... items) {
        this(Arrays.asList(items));
    }

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
