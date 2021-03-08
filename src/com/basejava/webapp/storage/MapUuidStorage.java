package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        // return new LinkedHashMap<>(storage).values().toArray(new Resume[0]);
        List<Resume> list = new ArrayList<>(storage.values());
        list.sort(Resume::compareTo);
        return list;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean elementExist(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }
}
