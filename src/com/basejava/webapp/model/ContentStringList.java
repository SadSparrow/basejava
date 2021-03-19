package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ContentStringList implements Content {
    private List<String> stringList = new ArrayList<>();

    public ContentStringList() {
    }

    public ContentStringList(List<String> stringList) {
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
