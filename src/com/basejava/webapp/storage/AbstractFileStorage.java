package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.io.File;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File file;

    public AbstractFileStorage(File file) {
        Objects.requireNonNull(file, "file must not be null");
        this.file = file;
    }

    @Override
    protected void doUpdate(Resume resume, File key) {
        //?
    }

    @Override
    protected void doSave(Resume resume, File key) {
        //?
    }

    @Override
    protected Resume getResume(File key) {
        return null; //?
    }

    @Override
    protected void doDelete(File key) {
        key.delete();
    }

    @Override
    protected boolean isResumeExist(File key) {
        return key.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(file, uuid);
    }

    @Override
    protected List<Resume> getList() {
        return null; //?
    }

    @Override
    public void clear() {
        file.delete();
    }

    @Override
    public int size() {
        return Objects.requireNonNull(file.list()).length;
    }
}
