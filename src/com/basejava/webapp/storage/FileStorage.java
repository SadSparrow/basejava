package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serialization.SerializationStrategy;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final SerializationStrategy serialization;

    protected FileStorage(File directory, SerializationStrategy serialization) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serialization, "SerializationStrategy must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath());
        }
        this.directory = directory;
        this.serialization = serialization;
    }

    @Override
    protected void doUpdate(Resume resume, File key) {
        try {
            serialization.doWrite(resume, new BufferedOutputStream(new FileOutputStream(key)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

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
            return serialization.doRead(new BufferedInputStream(new FileInputStream(key)));
        } catch (IOException e) {
            throw new StorageException("File read error", key.getName(), e);
        }
    }

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
    protected List<Resume> getAll() {
        return getFilesList().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private Stream<File> getFilesList() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error (null)");
        }
        return Arrays.stream(files);
    }
}