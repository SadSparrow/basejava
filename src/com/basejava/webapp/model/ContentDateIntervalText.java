package com.basejava.webapp.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ContentDateIntervalText implements Content {
    private List<ContentDateText> contentDateText = new ArrayList<>();

    public ContentDateIntervalText() {
    }

    public void addContentDatesText(String title, YearMonth d1, YearMonth d2, String text) {
        contentDateText.add(new ContentDateText(title, d1, d2, text));
    }

    public void addContentDatesText(String title, String d1, String d2, String text) {
        contentDateText.add(new ContentDateText(title, d1, d2, text));
    }

    public void addContentDateTillPresentText(String title, YearMonth d1, String p, String text) {
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
        YearMonth date1;
        YearMonth date2;
        String tillPresent;
        String text;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        private ContentDateText(String title, YearMonth date1, String text) {
            this.title = title;
            this.date1 = date1;
            this.text = text;
        }

        private ContentDateText(String title, String date1, String text) {
            this.title = title;
            this.date1 = YearMonth.parse(date1, formatter);
            this.text = text;
        }

        public ContentDateText(String title, YearMonth date1, YearMonth date2, String text) {
            this(title, date1, text);
            this.date2 = date2;
        }

        public ContentDateText(String title, String date1, String date2TillPresent, String text) {
            this(title, date1, text);
            try {
                this.date2 = YearMonth.parse(date2TillPresent, formatter);
            } catch (DateTimeParseException e) {
                this.tillPresent = date2TillPresent;
            }
        }

        public ContentDateText(String title, YearMonth date1, String tillPresent, String text) {
            this(title, date1, text);
            this.tillPresent = tillPresent;
        }

        public String getContentDate1Text() {
            return title + "\n" + date1 + "\n" + text;
        }

        public String getContentDatesText() {
            return title + "\n" + date1.format(formatter) + " - " + date2.format(formatter) + "\n" + text;
        }

        public String getContentDateTillPresentText() {
            return title + "\n" + date1.format(formatter) + " - " + tillPresent + "\n" + text;
        }
    }
}
