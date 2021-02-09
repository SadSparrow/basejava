package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("update (" + resume.getUuid() + ") successfully");
        } else {
            System.out.println("❌❌❌ UPDATE ERROR: no such resume (" + resume.getUuid() + ") in ArrayStorage ❌❌❌");
        }
    }

    @Override
    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("ArrayStorage is already full");
        } else if (index <= 0) {
            save(index, resume);
            size++;
        } else {
            System.out.println("❌❌❌ SAVE ERROR: such resume (" + resume.getUuid() + ") already exist ❌❌❌");
        }
    }

    protected abstract void save(int index, Resume resume);

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("❌❌❌ GET ERROR: no such resume (" + uuid + ") in ArrayStorage ❌❌❌");
            return null;
        }
        return storage[index];
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            delete(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("❌❌❌ DELETE ERROR: no such resume (" + uuid + ") in ArrayStorage ❌❌❌");
        }
    }

    protected abstract void delete(int index);

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);
}
