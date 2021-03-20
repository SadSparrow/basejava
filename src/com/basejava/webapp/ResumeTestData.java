package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume r = new Resume("uuid1", "Григорий Кислин");
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        Map<SectionType, Content> content = new EnumMap<>(SectionType.class);
        r.setContacts(contacts);
        r.setContent(content);

        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.MAIl, "gkislin@yandex.ru");

        content.put(SectionType.OBJECTIVE, new SimpleTextContent("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        content.put(SectionType.PERSONAL, new SimpleTextContent("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> listAchievment = new ArrayList<>();
        listAchievment.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievment.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievment.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        listAchievment.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        listAchievment.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        listAchievment.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        content.put(SectionType.ACHIEVEMENT, new StringListContent(listAchievment));

        List<String> listQualifications = new ArrayList<>();
        listQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        listQualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        listQualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        listQualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        listQualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        listQualifications.add("Python: Django.");
        listQualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        listQualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        listQualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX,");
        listQualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        listQualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis,");
        listQualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        listQualifications.add("Родной русский, английский \"upper intermediate\"");
        content.put(SectionType.QUALIFICATIONS, new StringListContent(listQualifications));

        Organization organizations = new Organization();
        organizations.addDatesText("Java Online Projects", "10/2013", "Сейчас", "Автор проекта.\nСоздание, организация и проведение Java онлайн проектов и стажировок.");
        organizations.addDatesText("Wrike", "10/2014", "01/2016", "Старший разработчик (backend)\nПроектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        organizations.addDatesText("RIT Center", "04/2012", "10/2014", "Java архитектор\nОрганизация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        organizations.addDatesText("Luxoft (Deutsche Bank)", "12/2010", "04/2012", "Ведущий программист\nУчастие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        organizations.addDatesText("Yota", "06/2008", "12/2010", "Ведущий специалист\nДизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        organizations.addDatesText("Enkata", "03/2007", "06/2008", "Разработчик ПО\nРеализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        organizations.addDatesText("Siemens AG", "01/2005", "02/2007", "Разработчик ПО\nРазработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        organizations.addDatesText("Alcatel", "09/1997", "01/2005", "Инженер по аппаратному и программному тестированию\nТестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        content.put(SectionType.EXPERIENCE, organizations);

        Organization education = new Organization();
        education.addDatesText("Coursera", "03/2013", "05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky");
        education.addDatesText("Luxoft", "03/2011", "04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        education.addDatesText("Siemens AG", "01/2005", "04/2005", "3 месяца обучения мобильным IN сетям (Берлин)");
        education.addDatesText("Alcatel", "09/1997", "03/1998", "6 месяцев обучения цифровым телефонным сетям (Москва)");
        education.addDatesText("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "09/1993", "07/1996", "Аспирантура (программист С, С++)");
        education.addDatesText("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "09/1987", "07/1993", "Инженер (программист Fortran, C)");
        education.addDatesText("Заочная физико-техническая школа при МФТИ", "09/1984", "06/1987", "Закончил с отличием");
        content.put(SectionType.EDUCATION, education);

        System.out.println(r.getFullName());
        System.out.println(r.getContacts());
        System.out.println(r.getContent());
    }
}
