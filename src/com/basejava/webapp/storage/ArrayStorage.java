package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    public void update(Resume r) {
        if (checkPresent(r.getUuid())) {
            new Resume().setUuid(r.getUuid());
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size > storage.length){
            System.out.println("ArrayStorage is already full");
        } else if (searchIndex(r.getUuid()) == -1) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("❌❌❌ ERROR: such resume already exist ❌❌❌");
        }
    }

    public Resume get(String uuid) {
        Resume r = null;
        if (checkPresent(uuid)) {
            r = storage[searchIndex(uuid)];
        }
        return r;
    }

    public void delete(String uuid) {
        if (checkPresent(uuid)) {
            storage[searchIndex(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private boolean checkPresent(String uuid) {
        if (searchIndex(uuid) == -1) {
            System.out.println("❌❌❌ ERROR: no such resume in ArrayStorage ❌❌❌");
            return false;
        }
        return true;
    }

    private int searchIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
