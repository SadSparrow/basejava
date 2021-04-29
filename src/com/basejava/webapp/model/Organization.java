package com.basejava.webapp.model;

import com.basejava.webapp.util.DateUtil;
import com.basejava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private Link homePage;
    private List<Period> period = new ArrayList<>();

    public Organization() {
    }

    public Organization(Link homePage, List<Period> period) {
        this.homePage = homePage;
        this.period = period;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Period> getPeriod() {
        return period;
    }

    public Organization(String name, String url, Period... periods) {
        this(new Link(name, url), Arrays.asList(periods));
    }

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        this.period.add(new Period(startDate, endDate, title, description));
    }

    public Organization(String name, String url, LocalDate startDate, String title, String description) {
        this.homePage = new Link(name, url);
        this.period.add(new Period(startDate, DateUtil.NOW, title, description));
    }

    public Organization(String name, String url, int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
        this.homePage = new Link(name, url);
        this.period.add(new Period(startYear, startMonth, endYear, endMonth, title, description));
    }

    public Organization(String name, String url, int startYear, Month startMonth, String title, String description) {
        this.homePage = new Link(name, url);
        this.period.add(new Period(startYear, startMonth, title, description));
    }

    public void addPeriod(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
        this.period.add(new Period(startYear, startMonth, endYear, endMonth, title, description));
    }

    public void addPeriod(int startYear, Month startMonth, String title, String description) {
        this.period.add(new Period(startYear, startMonth, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return period.equals(that.period);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + period.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\nOrganization{" + "homePage=" + homePage + period.toString() + '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Period() {
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public Period(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.NOW, title, description);
        }

        public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public Period(LocalDate startDate, String title, String description) {
            this(startDate, DateUtil.NOW, title, description);
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (!startDate.equals(period.startDate)) return false;
            if (!Objects.equals(endDate, period.endDate)) return false;
            if (!title.equals(period.title)) return false;
            return Objects.equals(description, period.description);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
