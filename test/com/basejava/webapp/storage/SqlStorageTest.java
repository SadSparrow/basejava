package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest{
    private static final SqlStorage SQL_STORAGE = Config.get().getStorage();

    public SqlStorageTest() {
        super(SQL_STORAGE);
    }
}