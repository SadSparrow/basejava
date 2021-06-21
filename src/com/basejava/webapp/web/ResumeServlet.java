package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        String submit = request.getParameter("submit");
        Resume r;
        if (!uuid.equals("newResume")) {
            r = sqlStorage.get(uuid);
        } else {
            r = new Resume("newResume", "");
        }
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.setContacts(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type);
            String[] strings = request.getParameterValues(type.name());

            if (strings != null) {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> r.setContent(type, new SimpleTextContent(request.getParameter(type.name())));
                    case ACHIEVEMENT, QUALIFICATIONS -> r.setContent(type, new StringListContent(Arrays.asList(strings)));
                    case EXPERIENCE, EDUCATION -> {
                        String[] orgs = request.getParameterValues(type.name());
                        String[] url = request.getParameterValues(type.name() + "url");
                        List<Organization> orgsList = new ArrayList<>();
                        for (int i = 0; i < orgs.length; i++) {
                            List<Organization.Period> period = new ArrayList<>();
                            Organization org = new Organization(new Link(orgs[i], url[i]), period);
                            orgsList.add(org);
                            String[] title = request.getParameterValues(i + type.name() + "title");
                            String[] description = request.getParameterValues(i + type.name() + "description");
                            String[] startMonth = request.getParameterValues(i + type.name() + "StartDateMonth");
                            String[] startYear = request.getParameterValues(i + type.name() + "StartDateYear");
                            String[] endMonth = request.getParameterValues(i + type.name() + "EndDateMonth");
                            String[] endYear = request.getParameterValues(i + type.name() + "EndDateYear");
                            if (title != null) {
                                for (int j = 0; j < title.length; j++) {
                                    period.add(new Organization.Period(Integer.parseInt(startYear[j]), Month.of(Integer.parseInt(startMonth[j])), Integer.parseInt(endYear[j]), Month.of(Integer.parseInt(endMonth[j])), title[j], description[j]));
                                }
                            }
                            r.setContent(type, new OrganizationContent(orgsList));
                        }
                    }
                }
            }
        }
        if (submit != null && submit.equals("submit") && r.getUuid().equals("newResume")) {
            r.setUuid(UUID.randomUUID().toString());
            sqlStorage.save(r);
        } else {
            sqlStorage.update(r);
        }
        response.sendRedirect("resume");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "delete_line" -> {
                int count = Integer.parseInt(request.getParameter("count"));
                SectionType type = SectionType.valueOf(request.getParameter("type"));
                r = sqlStorage.get(uuid);
                ((StringListContent) r.getContent(type)).getStringList().remove(count);
                sqlStorage.update(r);
            }
            case "add_line" -> {
                r = sqlStorage.get(uuid);
                SectionType type = SectionType.valueOf(request.getParameter("type"));
                ((StringListContent) r.getContent(type)).getStringList().add("");
                sqlStorage.update(r);
            }
            case "add_section" -> {
                r = sqlStorage.get(uuid);
                SectionType type = SectionType.valueOf(request.getParameter("type"));
                addSection(type, r);
                sqlStorage.update(r);
            }
            case "add_org" -> {
                r = sqlStorage.get(uuid);
                SectionType type = SectionType.valueOf(request.getParameter("type"));
                ((OrganizationContent) r.getContent(type)).getOrganizations().add(new Organization(new Link("write name", ""), new ArrayList<>()));
                sqlStorage.update(r);
            }
            case "add_period" -> {
                r = sqlStorage.get(uuid);
                int count = Integer.parseInt(request.getParameter("count"));
                SectionType type = SectionType.valueOf(request.getParameter("type"));
                Organization org = ((OrganizationContent) r.getContent(type)).getOrganizations().get(count);
                org.addPeriod(1111, Month.of(1), 1111, Month.of(1), "write title", "");
                sqlStorage.update(r);
            }
            case "view", "edit" -> r = sqlStorage.get(uuid);
            case "new" -> r = new Resume("newResume", "");
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                (action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private static void addSection(SectionType type, Resume r) {
        switch (type) {
            case OBJECTIVE, PERSONAL -> r.setContent(type, new SimpleTextContent(""));
            case ACHIEVEMENT, QUALIFICATIONS -> r.setContent(type, new StringListContent(new ArrayList<>()));
            case EXPERIENCE, EDUCATION -> r.setContent(type, new OrganizationContent(new ArrayList<>()));
        }
    }
}
