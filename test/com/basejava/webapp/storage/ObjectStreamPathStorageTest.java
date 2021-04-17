package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialization.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toPath(), new ObjectStreamSerializer()));
    }
}