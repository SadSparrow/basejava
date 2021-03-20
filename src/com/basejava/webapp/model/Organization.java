package com.basejava.webapp.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Content {
    private List<Experience> contentDateText = new ArrayList<>();

    public Organization() {
    }

    public Organization(List<Experience> contentDateText) {
        this.contentDateText = contentDateText;
    }

    public void addDatesText(String title, String d1, String d2, String text) {
        contentDateText.add(new Experience(title, d1, d2, text));
    }

    public void addDatesText(String title, YearMonth d1, YearMonth d2, String text) {
        contentDateText.add(new Experience(title, d1, d2, text));
    }

    public void addDateTillPresentText(String title, YearMonth d1, String p, String text) {
        contentDateText.add(new Experience(title, d1, p, text));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Experience dateText : contentDateText) {
            sb.append(dateText.getDatesText()).append("\n");
        }
        return sb.toString();
    }

    private static class Experience {
        String title;
        YearMonth date1;
        YearMonth date2;
        String tillPresent;
        String text;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        private Experience(String title, YearMonth date1, String text) {
            this.title = title;
            this.date1 = date1;
            this.text = text;
        }

        private Experience(String title, String date1, String text) {
            this.title = title;
            this.date1 = YearMonth.parse(date1, formatter);
            this.text = text;
        }

        public Experience(String title, YearMonth date1, YearMonth date2, String text) {
            this(title, date1, text);
            this.date2 = date2;
        }

        public Experience(String title, String date1, String date2TillPresent, String text) {
            this(title, date1, text);
            try {
                this.date2 = YearMonth.parse(date2TillPresent, formatter);
            } catch (DateTimeParseException e) {
                this.tillPresent = date2TillPresent;
            }
        }

        public Experience(String title, YearMonth date1, String tillPresent, String text) {
            this(title, date1, text);
            this.tillPresent = tillPresent;
        }

        public String getDatesText() {
            StringBuilder sb = new StringBuilder();
            sb.append(title).append("\n").append(date1.format(formatter)).append(" - ");
            sb.append(Objects.requireNonNullElseGet(tillPresent, () -> date2.format(formatter)));
            sb.append("\n").append(text);
            return sb.toString();
        }
    }
}
