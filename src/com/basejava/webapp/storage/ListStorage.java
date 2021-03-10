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
    protected void doUpdate(Resume resume, Object key) {
        storage.set((Integer) key, resume);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void doDelete(Object key) {
        int index = (Integer) key;
        storage.remove(index);
    }

    @Override
    public ArrayList<Resume> getList() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isResumeExist(Object key) {
        return (Integer) key >= 0;
    }
}
