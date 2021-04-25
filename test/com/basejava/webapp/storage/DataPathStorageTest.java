package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialization.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toPath(), new DataStreamSerializer()));
    }
}