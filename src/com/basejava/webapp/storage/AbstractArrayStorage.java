package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume resume) {
        int index = getIndex(resume.getUuid());
        storage[index] = resume;
    }

    @Override
    protected void doSave(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveElement(index, resume);
        size++;
    }

    protected abstract void saveElement(int index, Resume resume);

    @Override
    protected Resume getResume(Object key) {
        int index = getIndex((String) key);
        return storage[index];
    }

    @Override
    protected void doDelete(Object key) {
        int index = getIndex((String) key);
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteResume(int index);

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    @Override
    protected boolean elementExist(Object key) {
        return getIndex((String) key) >= 0;
    }
}
