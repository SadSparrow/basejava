package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialization.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toPath(), new JsonStreamSerializer()));
    }
}