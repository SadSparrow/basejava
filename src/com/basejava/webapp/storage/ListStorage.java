package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void update(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void checkOverflow(Resume resume) {
        //false
    }

    @Override
    protected void save(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void delete(int index) {
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
}
