package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
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
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("update (" + resume.getUuid() + ") successfully");
        } else {
            throw new NotExistStorageException("UPDATE", resume.getUuid());
        }
    }

    @Override
    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if (index <= 0) {
            save(index, resume);
            size++;
        } else {
            throw new ExistStorageException("SAVE", resume.getUuid());
        }
    }

    protected abstract void save(int index, Resume resume);

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException("GET", uuid);
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
            throw new NotExistStorageException("DELETE", uuid);
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
