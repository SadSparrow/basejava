package com.basejava.webapp;

import com.basejava.webapp.model.ContentDateIntervalText;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        //всё ещё в процессе
        Resume r = new Resume("uuid1", "Григорий Кислин");
        r.setPhoneSkypeMail("+7(921) 855-0482", "grigory.kislin", "gkislin@yandex.ru");
        System.out.println(r.getFullName());
        System.out.println(r.getContacts());

        r.setObjective("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        r.setPersonal("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        List<String> listAchievment = new ArrayList<>();
        listAchievment.add("\uF06C С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievment.add("\uF06C Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievment.add("\uF06C Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        listAchievment.add("\uF06C Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        listAchievment.add("\uF06C Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        listAchievment.add("\uF06C Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        r.setAchievement(listAchievment);

        List<String> listQualifications = new ArrayList<>();
        listQualifications .add("\uF06C JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications .add("\uF06C Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications .add("\uF06C DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        listQualifications .add("\uF06C MySQL, SQLite, MS SQL, HSQLDB");
        listQualifications .add("\uF06C Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        listQualifications .add("\uF06C XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        listQualifications .add("\uF06C Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        listQualifications .add("\uF06C Python: Django.");
        listQualifications .add("\uF06C JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        listQualifications .add("\uF06C Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        listQualifications .add("\uF06C Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX,");
        listQualifications .add("\uF06C Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        listQualifications .add("\uF06C администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis,");
        listQualifications .add("\uF06C Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        listQualifications .add("\uF06C Родной русский, английский \"upper intermediate\"");
        r.setQualifications(listQualifications);

        ContentDateIntervalText c = new ContentDateIntervalText();
        r.setExperience(c);
        c.addContentDatesText("Java Online Projects", "10/2013", "Сейчас", "Автор проекта.\nСоздание, организация и проведение Java онлайн проектов и стажировок.");
        c.addContentDatesText("Wrike", "10/2014", "01/2016", "Старший разработчик (backend)\nПроектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        System.out.println(r.getContent());


    }
}
