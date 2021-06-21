<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.AbstractContent" %>
<%@ page import="com.basejava.webapp.model.StringListContent" %>
<%@ page import="com.basejava.webapp.model.OrganizationContent" %>
<%@ page import="com.basejava.webapp.model.Organization" %>
<%@ page import="com.basejava.webapp.model.Organization.Period" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" required size=50 value="${resume.fullName}"></dd>
        </dl>
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h2>Секции:</h2>
        <c:forEach var="type" items="<%=SectionType.values()%>">
        <c:if test="${resume.getContent(type) == null}">
            <p><h3><c:out value="${type.getTitle()}"/></h3></p>
            <p>Add Section<a href="resume?uuid=${resume.uuid}&action=add_section&type=${type}"><img src="img/add.png"></a></p>
        </c:if>
        <c:if test="${resume.getContent(type) != null}">
        <c:set var="content" value="${resume.getContent(type)}"/>
            <jsp:useBean id="content" type="com.basejava.webapp.model.AbstractContent"/>
            <dl>
                <dt><h3><c:out value="${type.title}"/></h3></dt>
                <c:choose>
                    <c:when test="${type== 'OBJECTIVE' || type== 'PERSONAL'}">
                    <c:set var="strings" value="${content}"/>
                        <dd><input type="text" name="${type}" size=80 value="${fn:replace(strings, '\"', '&quot;')}"></dd>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                        <c:forEach var="list" items="<%=((StringListContent) content).getStringList()%>" varStatus="count">
                        <c:set var="strings" value="${list}"/>
                            <dd>
                                <input type="text" name="${type}" size=100 value="${fn:replace(strings, '\"', '&quot;')}">
                                <a href="resume?uuid=${resume.uuid}&action=delete_line&count=${count.index}&type=${type}"><img src="img/delete.png"></a>
                            </dd>
                        </c:forEach>
                        <p>Add line
                            <a href="resume?uuid=${resume.uuid}&action=add_line&type=${type}"><img src="img/add.png"></a></p>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <p><a href="resume?uuid=${resume.uuid}&action=add_org&type=${type}"><img src="img/add.png"></a></p>
                        <c:forEach var="orgList" items="<%=((OrganizationContent) content).getOrganizations()%>" varStatus="count">
                            <b><input type="text" name="${type}" required size=100 value="${orgList.getHomePage().getName()}">
                                <a href="resume?uuid=${resume.uuid}&action=add_period&count=${count.index}&type=${type}"><img src="img/add.png"></a>
                            </b><br>
                            <input type="text" name="${type}url" size=100 value="${orgList.getHomePage().getUrl()}">
                                <c:forEach var="period" items="${orgList.getPeriod()}">
                                    <p>
                        Start date: <input type="text" name="${count.index}${type}StartDateMonth" required size=10 value="${period.getStartDate().getMonthValue()}">
                                   /<input type="text" name="${count.index}${type}StartDateYear" required size=10 value="${period.getStartDate().getYear()}">
                        End date: <input type="text" name="${count.index}${type}EndDateMonth" required size=10 value="${period.getEndDate().getMonthValue()}">
                                   /<input type="text" name="${count.index}${type}EndDateYear" required size=10 value="${period.getEndDate().getYear()}">
                                    </p>
                                    <c:set var="title" value="${period.getTitle()}"/>
                                    <p><input type="text" name="${count.index}${type}title" required size=100 value="${fn:replace(title, '\"', '&quot;')}"></p>
                                    <c:set var="description" value="${period.getDescription()}"/>
                                    <p><input type="text" name="${count.index}${type}description" size=100 value="${fn:replace(description, '\"', '&quot;')}"></p>
                                </c:forEach>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>Undefined</p>
                    </c:otherwise>
                    </c:choose>
            </dl>
            </c:if>
        </c:forEach>
        <hr>
        <button name="submit" value="submit">Сохранить</button>
        <button name="undo" value="undo" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>