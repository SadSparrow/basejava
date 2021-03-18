package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContentDateIntervalText implements Content {
    private List<ContentDateText> contentDateText = new ArrayList<>();

    public ContentDateIntervalText() {
    }

    public void addContentDatesText(String title, LocalDate d1, LocalDate d2, String text) {
        contentDateText.add(new ContentDateText(title, d1, d2, text));
    }

    public void addContentDateTillPresentText(String title, LocalDate d1, String p, String text) {
        contentDateText.add(new ContentDateText(title, d1, p, text));
    }

    public ContentDateIntervalText(List<ContentDateText> contentDateText) {
        this.contentDateText = contentDateText;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ContentDateText dateText : contentDateText) {
            sb.append(dateText.getContentDate1Text()).append("\n");
        }
        return sb.toString();
    }

    class ContentDateText {
        String title;
        LocalDate date1;
        LocalDate date2;
        String tillPresent;
        String text;

        public ContentDateText(String title, LocalDate date1, LocalDate date2, String text) {
            this.title = title;
            this.date1 = date1;
            this.date2 = date2;
            this.text = text;
        }

        public ContentDateText(String title, LocalDate date1, String tillPresent, String text) {
            this.title = title;
            this.date1 = date1;
            this.tillPresent = tillPresent;
            this.text = text;
        }

        public String getContentDate1Text() {
            return title + "\n" + date1 + "\n" + text;
        }

        public String getContentDatesText() {
            return title + "\n" + date1 + "\n" + date2 + "\n" + text;
        }

        public String getContentDateTillPresentText() {
            return title + "\n" + date1 + "\n" + tillPresent + "\n" + text;
        }
    }
}
