package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume resume) {
        int index = getIndex(resume.getUuid());
        storage.set(index, resume);
    }

    @Override
    protected void doSave(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(Object key) {
        int index = getIndex((String) key);
        return storage.get(index);
    }

    @Override
    protected void doDelete(Object key) {
        int index = getIndex((String) key);
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return new ArrayList<>(storage).toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean elementExist(Object key) {
        for (Resume resume : storage) {
            if (resume.getUuid().equals(key)) {
                return true;
            }
        }
        return false;
    }
}
