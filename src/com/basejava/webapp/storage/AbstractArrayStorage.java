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
    protected void update(int index, Resume resume) {
        storage[index] = resume;
    }


    @Override
    protected void save(int index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveElement(index, resume);
            size++;
        }
    }

    protected abstract void saveElement(int index, Resume resume);

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected void delete(int index) {
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
}
