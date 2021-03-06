package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.ArrayStorage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Cat Tom");
        Resume r2 = new Resume("uuid2", "Mouse Jerry");
        Resume r3 = new Resume("uuid3", "Cat Meow");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.update(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.update(r1);

        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        /*for (int i = 1; i < 10; i++) {
            Resume r = new Resume("uuid" + i);
            ARRAY_STORAGE.save(r);
        }
        printAll();*/
        System.out.println("Size: " + ARRAY_STORAGE.size());
        ARRAY_STORAGE.delete("uuid3");
        ARRAY_STORAGE.delete("uuid6");
        ARRAY_STORAGE.delete("uuid9");
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get uuid1000: " + ARRAY_STORAGE.get("uuid1000"));

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
