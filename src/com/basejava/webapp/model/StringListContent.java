package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class StringListContent implements Content {
    private List<String> stringList = new ArrayList<>();

    public StringListContent() {
    }

    public StringListContent(List<String> stringList) {
        this.stringList = stringList;
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
