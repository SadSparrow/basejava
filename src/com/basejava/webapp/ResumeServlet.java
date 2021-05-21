package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SqlStorage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {
    private final SqlStorage sqlStorage = Config.get().getStorage();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        printResume(response);
    }

    private void printResume(HttpServletResponse response) throws IOException {
        List<Resume> list = sqlStorage.getAllSorted();
        PrintWriter writer = response.getWriter();

        writer.println("<table width=\"35%\" border=\"1\">" +
                "<tr>" +
                "<th>UUID</th>" +
                "<th>Full Name</th>" +
                "</tr>");

        for (Resume r : list) {
            writer.println("<tr>");
            writer.println("<td>" + r.getUuid() + "</td>");
            writer.println("<td>" + r.getFullName() + "</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");
    }
}
