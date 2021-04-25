package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serialization.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializationStrategy serialization;

    protected PathStorage(Path directory, SerializationStrategy serialization) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serialization, "SerializationStrategy must not be null");
        this.directory = directory;
        this.serialization = serialization;
    }

    @Override
    protected void doUpdate(Resume resume, Path key) {
        try {
            serialization.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(key)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path key) {
        try {
            Files.createFile(key);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + key.toAbsolutePath(), getFileName(key), e);
        }
        doUpdate(resume, key);
    }

    @Override
    protected Resume getResume(Path key) {
        try {
            return serialization.doRead(new BufferedInputStream(Files.newInputStream(key)));
        } catch (IOException e) {
            throw new StorageException("Path read error", getFileName(key), e);
        }
    }

    @Override
    protected void doDelete(Path key) {
        try {
            Files.deleteIfExists(key);
        } catch (IOException e) {
            throw new StorageException("Path delete error", getFileName(key), e);
        }
    }

    @Override
    protected boolean isResumeExist(Path key) {
        //return Files.isRegularFile(key); в решении так
        return Files.exists(key);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected List<Resume> getAll() {
        return getFilesList().map(this::getResume).collect(Collectors.toList()); //в java 16 вместо .collect(Collectors.toList()) просто toList()
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}