package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialization.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toPath(), new XmlStreamSerializer()));
    }
}