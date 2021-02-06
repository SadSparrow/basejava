package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void update(Resume resume) {
        if (searchIndex(resume.getUuid()) != -1) {
            storage[searchIndex(resume.getUuid())] = resume;
        } else {
            System.out.println("❌❌❌ UPDATE ERROR: no such resume (" + resume.getUuid() + ") in ArrayStorage ❌❌❌");
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size > storage.length) {
            System.out.println("ArrayStorage is already full");
        } else if (searchIndex(resume.getUuid()) == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("❌❌❌ SAVE ERROR: such resume (" + resume.getUuid() + ") already exist ❌❌❌");
        }
    }

    public Resume get(String uuid) {
        Resume resume = null;
        if (searchIndex(uuid) != -1) {
            resume = storage[searchIndex(uuid)];
        } else {
            System.out.println("❌❌❌ GET ERROR: no such resume (" + uuid + ") in ArrayStorage ❌❌❌");
        }
        return resume;
    }

    public void delete(String uuid) {
        if (searchIndex(uuid) != -1) {
            storage[searchIndex(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("❌❌❌ DELETE ERROR: no such resume (" + uuid + ") in ArrayStorage ❌❌❌");
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

    private int searchIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
