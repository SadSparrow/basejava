package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class PathStorage extends AbstractPathStorage {
    private final Serialization serialization;

    public PathStorage(Path directory, Serialization serialization) {
        super(directory);
        this.serialization = serialization;
    }

    public PathStorage(String dir, Serialization serialization) {
        super(dir);
        this.serialization = serialization;
    }

    @Override
    protected void doWrite(Resume resume, OutputStream file) throws IOException {
        serialization.doWrite(resume, file);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return serialization.doRead(is);
    }
}
