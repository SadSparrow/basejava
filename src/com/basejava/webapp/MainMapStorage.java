package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.MapStorage;

public class MainMapStorage {
    static final MapStorage MAP_STORAGE = new MapStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Resume r5 = new Resume("uuid5");
        // MAP_STORAGE.update(r1);
        MAP_STORAGE.save(r5);
        MAP_STORAGE.save(r3);
        MAP_STORAGE.save(r2);
        MAP_STORAGE.save(r1);
        // MAP_STORAGE.save(r1);
        MAP_STORAGE.update(r2);

        System.out.println("Get r2: " + MAP_STORAGE.get(r2.getUuid()));
        printAll();
        System.out.println("Size: " + MAP_STORAGE.size());
        MAP_STORAGE.delete("uuid1");
        printAll();
        System.out.println("Size: " + MAP_STORAGE.size());
        //MAP_STORAGE.update(r1);
        //System.out.println("Get dummy: " + MAP_STORAGE.get("dummy"));
        MAP_STORAGE.clear();
        printAll();
        System.out.println("Size: " + MAP_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : MAP_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
