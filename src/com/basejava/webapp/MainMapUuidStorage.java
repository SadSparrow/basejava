package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.MapUuidStorage;

public class MainMapUuidStorage {
    static final MapUuidStorage MAP_STORAGE = new MapUuidStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Cat Tom");
        Resume r2 = new Resume("uuid2", "Mouse Jerry");
        Resume r3 = new Resume("uuid3", "Cat Meow");
        Resume r5 = new Resume("uuid5", "Dog dog");
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
        for (Resume r : MAP_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
