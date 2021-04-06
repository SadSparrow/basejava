package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationAbstractContent extends AbstractContent {
    private final List<Organization> organizations;

    public OrganizationAbstractContent(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationAbstractContent that = (OrganizationAbstractContent) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}