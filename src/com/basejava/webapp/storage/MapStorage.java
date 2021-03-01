package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void update(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void save(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void delete(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    public Resume[] getAll() {
        return new LinkedHashMap<>(storage).values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected int getIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return 1;
        }
        return -1;
    }
}
