package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Organization {
    private final Link homePage;
    private final List<Period> period = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        this.period.add(new Period(startDate, endDate, title, description));
    }

    public void addPeriod(LocalDate startDate, LocalDate endDate, String title, String description) {
        this.period.add(new Period(startDate, endDate, title, description));
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
}
