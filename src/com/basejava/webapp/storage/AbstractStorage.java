package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            update(index, resume);
            System.out.println("update (" + resume.getUuid() + ") successfully");
        } else {
            throw new NotExistStorageException("UPDATE", resume.getUuid());
        }
    }

    protected abstract void update(int index, Resume resume);

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        checkOverflow(resume);
        if (index <= 0) {
            save(index, resume);
        } else {
            throw new ExistStorageException("SAVE", resume.getUuid());
        }
    }

    protected abstract void checkOverflow(Resume resume);

    protected abstract void save(int index, Resume resume);

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException("GET", uuid);
        }
        return getResume(index);
    }

    protected abstract Resume getResume(int index);

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            delete(index);
        } else {
            throw new NotExistStorageException("DELETE", uuid);
        }
    }

    protected abstract void delete(int index);

    protected abstract int getIndex(String uuid);
}
