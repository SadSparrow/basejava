package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume r = createResume("uuid1", "Григорий Кислин");
        System.out.println(r.getFullName());
        for (ContactType c : ContactType.values()) {
            System.out.println(c.getTitle() + r.getContact(c));
        }
        for (SectionType s : SectionType.values()) {
            System.out.println(s.getTitle() + "\n" + r.getContent(s));
        }
    }

    public static Resume createResume(String uuid, String fullname) {
        Resume r = new Resume(uuid, fullname);
        r.setContacts(ContactType.PHONE, "+7(921) 855-0482");
        r.setContacts(ContactType.SKYPE, "grigory.kislin");
        r.setContacts(ContactType.MAIl, "gkislin@yandex.ru");

        r.setContent(SectionType.OBJECTIVE, new SimpleTextContent("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r.setContent(SectionType.PERSONAL, new SimpleTextContent("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> listAchievment = new ArrayList<>();
        listAchievment.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievment.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievment.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        listAchievment.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        listAchievment.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        listAchievment.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        r.setContent(SectionType.ACHIEVEMENT, new StringListContent(listAchievment));

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
        r.setContent(SectionType.QUALIFICATIONS, new StringListContent(listQualifications));

        List<Organization> organizations = new ArrayList<>();
        organizations.add(new Organization("Java Online Projects", "https://javaops.ru/", LocalDate.ofYearDay(2013, 10), null, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        organizations.add(new Organization("Wrike", "https://www.wrike.com/vj/", LocalDate.ofYearDay(2014, 10), LocalDate.ofYearDay(2016, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        organizations.add(new Organization("RIT Center", null, LocalDate.ofYearDay(2012, 4), LocalDate.ofYearDay(2014, 10), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        organizations.add(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", LocalDate.ofYearDay(2010, 12), LocalDate.ofYearDay(2012, 4), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        organizations.add(new Organization("Yota", "https://www.yota.ru/", LocalDate.ofYearDay(2008, 6), LocalDate.ofYearDay(2010, 12), "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        organizations.add(new Organization("Enkata", "https://www.pega.com/products/platform/robotic-process-automation", LocalDate.ofYearDay(2007, 3), LocalDate.ofYearDay(2008, 6), "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        organizations.add(new Organization("Siemens AG", "https://new.siemens.com/ru/ru.html", LocalDate.ofYearDay(2005, 1), LocalDate.ofYearDay(2007, 2), "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        organizations.add(new Organization("Alcatel", "http://www.alcatel.ru/", LocalDate.ofYearDay(1997, 9), LocalDate.ofYearDay(2005, 1), "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        r.setContent(SectionType.EXPERIENCE, new OrganizationAbstractContent(organizations));

        List<Organization> education = new ArrayList<>();
        education.add(new Organization("Coursera", "https://www.coursera.org/learn/progfun1", LocalDate.ofYearDay(2013, 3), LocalDate.ofYearDay(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky", null));
        education.add(new Organization("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html", LocalDate.ofYearDay(2011, 3), LocalDate.ofYearDay(2011, 4), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null));
        education.add(new Organization("Siemens AG", "https://new.siemens.com/ru/ru.html", LocalDate.ofYearDay(2005, 1), LocalDate.ofYearDay(2005, 4), "3 месяца обучения мобильным IN сетям (Берлин)", null));
        education.add(new Organization("Alcatel", "http://www.alcatel.ru/", LocalDate.ofYearDay(1997, 9), LocalDate.ofYearDay(1998, 3), "6 месяцев обучения цифровым телефонным сетям (Москва)", null));
        Organization spb = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "https://itmo.ru/ru/", LocalDate.ofYearDay(1993, 9), LocalDate.ofYearDay(1996, 7), "Аспирантура (программист С, С++)", null);
        spb.addPeriod(LocalDate.ofYearDay(1987, 7), LocalDate.ofYearDay(1993, 7), "Инженер (программист Fortran, C)", null);
        education.add(spb);
        education.add(new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/", LocalDate.ofYearDay(1984, 9), LocalDate.ofYearDay(1987, 6), "Закончил с отличием", null));
        r.setContent(SectionType.EDUCATION, new OrganizationAbstractContent(education));
        return r;
    }
}
