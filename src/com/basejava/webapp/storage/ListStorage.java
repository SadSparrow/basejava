package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Integer key) {
        storage.set(key, resume);
    }

    @Override
    protected void doSave(Resume resume, Integer key) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(Integer key) {
        int index = key;
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

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isResumeExist(Integer key) {
        return key >= 0;
    }
}
