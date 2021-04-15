package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath());
        }
        this.directory = directory;
    }

    @Override
    protected void doUpdate(Resume resume, File key) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(key)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    protected abstract void doWrite(Resume resume, OutputStream file) throws IOException;

    @Override
    protected void doSave(Resume resume, File key) {
        try {
            key.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + key.getAbsolutePath(), key.getName(), e);
        }
        doUpdate(resume, key);
    }

    @Override
    protected Resume getResume(File key) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(key)));
        } catch (IOException e) {
            throw new StorageException("File read error", key.getName(), e);
        }
    }

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void doDelete(File key) {
        if (!key.delete()) {
            throw new StorageException("File delete error", key.getName());
        }

    }

    @Override
    protected boolean isResumeExist(File key) {
        return key.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected List<Resume> getList() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(getResume(file));
        }
        return list;
    }


    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.list(), "Directory read error (null)").length;
    }

    abstract void testClass();
}