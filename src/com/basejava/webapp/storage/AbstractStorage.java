package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        if (elementExist(resume.getUuid())) {
            doUpdate(resume);
            System.out.println("update (" + resume.getUuid() + ") successfully");
        } else {
            throw new NotExistStorageException("UPDATE", resume.getUuid());
        }
    }

    protected abstract void doUpdate(Resume resume);

    @Override
    public void save(Resume resume) {
        if (!elementExist(resume.getUuid())) {
            doSave(resume);
        } else {
            throw new ExistStorageException("SAVE", resume.getUuid());
        }
    }

    protected abstract void doSave(Resume resume);

    @Override
    public Resume get(String uuid) {
        if (!elementExist(uuid)) {
            throw new NotExistStorageException("GET", uuid);
        }
        return getResume(uuid);
    }

    protected abstract Resume getResume(Object key);

    @Override
    public void delete(String uuid) {
        if (elementExist(uuid)) {
            doDelete(uuid);
        } else {
            throw new NotExistStorageException("DELETE", uuid);
        }
    }

    protected abstract void doDelete(Object key);

    protected abstract boolean elementExist(Object key);
}
