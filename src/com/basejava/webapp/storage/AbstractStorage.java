package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<K> implements Storage {
    protected static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume resume) {
        K key = getKeyIfElementExist("UPDATE", resume.getUuid());
        doUpdate(resume, key);
        LOG.info("Update (" + resume.getUuid() + ") successfully");
    }

    protected abstract void doUpdate(Resume resume, K key);

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        K key = getKeyIfElementNotExist(resume.getUuid());
        doSave(resume, key);
    }

    protected abstract void doSave(Resume resume, K key);

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        K key = getKeyIfElementExist("GET", uuid);
        return getResume(key);
    }

    protected abstract Resume getResume(K key);

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        K key = getKeyIfElementExist("DELETE", uuid);
        doDelete(key);
    }

    protected abstract void doDelete(K key);

    protected abstract boolean isResumeExist(K key);

    protected abstract K getSearchKey(String uuid);

    private K getKeyIfElementNotExist(String uuid) {
        K key = getSearchKey(uuid);
        if (isResumeExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException("SAVE", uuid);
        }
        return key;
    }

    private K getKeyIfElementExist(String methodName, String uuid) {
        K key = getSearchKey(uuid);
        if (!isResumeExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(methodName, uuid);
        }
        return key;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = getAll();
        list.sort(Resume::compareTo);
        return list;
    }

    protected abstract List<Resume> getAll();
}
