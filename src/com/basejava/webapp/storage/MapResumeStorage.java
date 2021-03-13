package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage{
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
        return (Resume) key;
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    public ArrayList<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isResumeExist(Object key) {
        return key != null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }
}
