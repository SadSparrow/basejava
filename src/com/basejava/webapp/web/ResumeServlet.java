package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
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
            if (HtmlUtil.isEmpty(value)) {
                r.setContacts(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());

            if (HtmlUtil.isEmpty(value) && HtmlUtil.isEmpty(values)) {
                r.getContents().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> r.setContent(type, new SimpleTextContent(value));
                    case ACHIEVEMENT, QUALIFICATIONS -> r.setContent(type, new StringListContent(value.split("\\n")));
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> orgs = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (HtmlUtil.isEmpty(name)) {
                                List<Organization.Period> periods = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startMonth = request.getParameterValues(i + type.name() + "StartDateMonth");
                                String[] startYear = request.getParameterValues(i + type.name() + "StartDateYear");
                                String[] endMonth = request.getParameterValues(i + type.name() + "EndDateMonth");
                                String[] endYear = request.getParameterValues(i + type.name() + "EndDateYear");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (HtmlUtil.isEmpty(titles[j])) {
                                        periods.add(new Organization.Period(Integer.parseInt(startYear[j]), Month.of(Integer.parseInt(startMonth[j])),
                                                Integer.parseInt(endYear[j]), Month.of(Integer.parseInt(endMonth[j])), titles[j], descriptions[j]));
                                    }
                                }
                                orgs.add(new Organization(new Link(name, urls[i]), periods));
                            }
                        }
                        r.setContent(type, new OrganizationContent(orgs));
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
            case "view" -> r = sqlStorage.get(uuid);
            case "edit" -> {
                r = sqlStorage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractContent content = r.getContent(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (content == null) {
                                content = SimpleTextContent.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (content == null) {
                                content = StringListContent.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION: {
                            OrganizationContent orgSection = (OrganizationContent) content;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization org : orgSection.getOrganizations()) {
                                    List<Organization.Period> emptyFirstPeriods = new ArrayList<>();
                                    emptyFirstPeriods.add(Organization.Period.EMPTY);
                                    emptyFirstPeriods.addAll(org.getPeriod());
                                    emptyFirstOrganizations.add(new Organization(org.getHomePage(), emptyFirstPeriods));
                                }
                            }
                            content = new OrganizationContent(emptyFirstOrganizations);
                        }
                        break;
                    }
                    r.setContent(type, content);
                }
            }
            case "new" -> r = new Resume("newResume", "");
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                (action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
