package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.ListStorage;

public class MainListStorage {
    static final ListStorage LIST_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Resume r5 = new Resume("uuid5");
        // LIST_STORAGE.update(r1);
        LIST_STORAGE.save(r5);
        LIST_STORAGE.save(r3);
        LIST_STORAGE.save(r2);
        LIST_STORAGE.save(r1);
        LIST_STORAGE.update(r2);

        System.out.println("Get r2: " + LIST_STORAGE.get(r2.getUuid()));
        printAll();
        System.out.println("Size: " + LIST_STORAGE.size());
        LIST_STORAGE.delete("uuid1");
        printAll();
        System.out.println("Size: " + LIST_STORAGE.size());

        LIST_STORAGE.clear();
        printAll();
        System.out.println("Size: " + LIST_STORAGE.size());
        //System.out.println("Get dummy: " + LIST_STORAGE.get("dummy"));
        System.out.println("Get uuid1000: " + LIST_STORAGE.get("uuid1000"));

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : LIST_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
