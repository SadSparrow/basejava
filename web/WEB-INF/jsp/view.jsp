<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.contents}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.AbstractContent>"/>
                <tr>
                    <td valign="middle"><h3><c:out value="${sectionEntry.getKey().getTitle()}"/></h3></td>
                </tr>
                    <c:choose>
                    <c:when test="${sectionEntry.getKey().name().equals('OBJECTIVE') || sectionEntry.getKey().name().equals('PERSONAL')}">
                        <tr><td><c:out value="${sectionEntry.getValue()}"/></td></tr>
                    </c:when>
                    <c:when test="${sectionEntry.getKey().name().equals('ACHIEVEMENT') || sectionEntry.getKey().name().equals('QUALIFICATIONS')}">
                        <c:set var="simpleTextContent" value="${resume.getContent(sectionEntry.getKey())}"/> 
                        <c:forEach var="stringList" items="${simpleTextContent.getStringList()}">
                            <tr><td><li><c:out value="${stringList}"/></li></td></tr>
                        </c:forEach>
                    </c:when>
                    <c:when test="${sectionEntry.getKey().name().equals('EXPERIENCE') || sectionEntry.getKey().name().equals('EDUCATION')}">
                        <c:set var="organizationContent" value="${resume.getContent(sectionEntry.getKey())}"/>
                        <c:forEach var="orgList" items="${organizationContent.getOrganizations()}">
                            <c:set var="homePage" value="${orgList.getHomePage()}"/>
                                <tr><td><a href="${homePage.getUrl()}"><c:out value="${homePage.getName()}"/></a></td>
                                <td><c:out value="${period}"/></td></tr>
                                <c:forEach var="period" items="${orgList.getPeriod()}">
                                    <tr><td>
                                    <c:out value="${period.getStartDate().getMonthValue()}"/>/<c:out value="${period.getStartDate().getYear()}"/> -
                                    <c:out value="${period.getEndDate().getMonthValue()}"/>/<c:out value="${period.getEndDate().getYear()}"/>
                                    </td></tr>
                                    <tr><td><b><c:out value="${period.getTitle()}"/></b></td></tr>
                                    <tr><td><c:out value="${period.getDescription()}"/></td></tr>
                                </c:forEach>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>Undefined</p>
                    </c:otherwise>
                    </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>