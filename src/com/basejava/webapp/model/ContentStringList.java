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

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }
}
