package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    protected void doUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveElement((Integer) index, resume);
        size++;
    }

    protected abstract void saveElement(int index, Resume resume);

    @Override
    protected Resume getResume(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void doDelete(Object index) {
        deleteResume((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteResume(int index);

    @Override
    public List<Resume> getAllSorted() {
        //return Arrays.copyOf(storage, size);
        List<Resume> list = new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, size)));
        list.sort(Resume::compareTo);
        return list;
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract Integer getSearchKey(String uuid); //ковариация - какая польза в данном случае?

    @Override
    protected boolean elementExist(Object key) {
        return (Integer) key >= 0;
    }
}
