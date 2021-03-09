package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SortedArrayStorage;

public class MainSortedArrayStorage {
    static final SortedArrayStorage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {

        Resume r1 = new Resume("uuid1", "Cat Tom");
        Resume r2 = new Resume("uuid2", "Mouse Jerry");
        Resume r3 = new Resume("uuid3", "Cat Meow");
        Resume r5 = new Resume("uuid5", "Dog dog");

        SORTED_ARRAY_STORAGE.update(r1);
        SORTED_ARRAY_STORAGE.save(r5);
        SORTED_ARRAY_STORAGE.save(r3);
        SORTED_ARRAY_STORAGE.save(r2);
        SORTED_ARRAY_STORAGE.save(r2);
        SORTED_ARRAY_STORAGE.update(r2);

        System.out.println("Get r2: " + SORTED_ARRAY_STORAGE.get(r2.getUuid()));
        printAll();
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
        SORTED_ARRAY_STORAGE.delete("uuid1");
        SORTED_ARRAY_STORAGE.delete("uuid19");
        SORTED_ARRAY_STORAGE.delete("uuid11");
        printAll();
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

        SORTED_ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
        System.out.println("Get dummy: " + SORTED_ARRAY_STORAGE.get("dummy"));
        System.out.println("Get uuid1000: " + SORTED_ARRAY_STORAGE.get("uuid1000"));

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : SORTED_ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
