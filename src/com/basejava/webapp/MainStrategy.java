package com.basejava.webapp;

import com.basejava.webapp.storage.*;

import java.io.File;

public class MainStrategy {
    public static void main(String[] args) {
        File dir = new File("C:/Users/Ulmon/Desktop/project/forResume");
        SerializationStrategy s1 = new Serialization();
        SerializationStrategy s2 = new Serialization2();

        PathStorage p1 = new PathStorage(dir.toPath(), s1);
        PathStorage p2 = new PathStorage(dir.toPath(), s2);

        FileStorage f1 = new FileStorage(dir, s1);
        FileStorage f2 = new FileStorage(dir, s2);

        p1.testClass();
        p2.testClass();
        System.out.println("____________________");
        f1.testClass();
        f2.testClass();
    }
}
