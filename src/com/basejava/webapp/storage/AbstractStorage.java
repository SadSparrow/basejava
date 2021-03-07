package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object key = getKeyIfElementExist("UPDATE", resume.getUuid());
        doUpdate(resume, key);
        System.out.println("update (" + resume.getUuid() + ") successfully");
    }

    protected abstract void doUpdate(Resume resume, Object key);

    @Override
    public void save(Resume resume) {
        Object key = getKeyIfElementNotExist(resume.getUuid());
        doSave(resume, key);
    }

    protected abstract void doSave(Resume resume, Object key);

    @Override
    public Resume get(String uuid) {
        Object key = getKeyIfElementExist("GET", uuid);
        return getResume(key);
    }

    protected abstract Resume getResume(Object key);

    @Override
    public void delete(String uuid) {
        Object key = getKeyIfElementExist("DELETE", uuid);
        doDelete(key);
    }

    protected abstract void doDelete(Object key);

    protected abstract boolean elementExist(Object key);

    protected abstract Object getSearchKey(String uuid);

    private Object getKeyIfElementNotExist(String uuid) {
        Object key = getSearchKey(uuid);
        if (elementExist(key)) {
            throw new ExistStorageException("SAVE", uuid);
        }
        return key;
    }

    private Object getKeyIfElementExist(String methodName, String uuid) {
        Object key = getSearchKey(uuid);
        if (!elementExist(key)) {
            throw new NotExistStorageException(methodName, uuid);
        }
        return key;
    }
}
