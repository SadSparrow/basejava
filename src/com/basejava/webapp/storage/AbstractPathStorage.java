package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(Path directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = directory;
    }

    protected AbstractPathStorage(String dir) {
        this(Paths.get(dir));
    }

    @Override
    protected void doUpdate(Resume resume, Path key) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(key)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    @Override
    protected void doSave(Resume resume, Path key) {
        try {
            Files.createFile(key);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + key.toAbsolutePath(), key.getFileName().toString(), e);
        }
        doUpdate(resume, key);
    }

    @Override
    protected Resume getResume(Path key) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(key)));
        } catch (IOException e) {
            throw new StorageException("Path read error", key.getFileName().toString(), e);
        }
    }

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void doDelete(Path key) {
        try {
            Files.deleteIfExists(key);
        } catch (IOException e) {
            throw new StorageException("File delete error", key.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isResumeExist(Path key) {
        return Files.exists(key);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected List<Resume> getList() {
        try {
            return Files.list(directory).forEach(this::getResume); //?
        } catch (IOException e) {
            throw new StorageException("getList error", "", e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error ", "(clear)", e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error ", "(size)", e);
        }
    }
}
