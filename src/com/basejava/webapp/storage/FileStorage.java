package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage {
    private final SerializationStrategy serialization;

    public FileStorage(File directory, SerializationStrategy serialization) {
        super(directory);
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

    @Override
    public void testClass() {
        System.out.println("from FileStorage");
        serialization.testInterface();
    }
}
