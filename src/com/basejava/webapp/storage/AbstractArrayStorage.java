package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        if (size >= STORAGE_LIMIT) {
            LOG.warning("Storage overflow " + resume.getUuid());
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveElement(index, resume);
        size++;
    }

    protected abstract void saveElement(int index, Resume resume);

    @Override
    protected Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    protected void doDelete(Integer index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteResume(int index);

    @Override
    public List<Resume> getList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract Integer getSearchKey(String uuid); //ковариация

    @Override
    protected boolean isResumeExist(Integer key) {
        return key >= 0;
    }
}
