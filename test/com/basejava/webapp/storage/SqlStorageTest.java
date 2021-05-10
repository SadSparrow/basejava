package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest{
    private static final String DB_URL = Config.get().getDbUrl();
    private static final String DB_USER = Config.get().getDbUser();
    private static final String DB_PASSWORD = Config.get().getDbPassword();

    public SqlStorageTest() {
        super(new SqlStorage(DB_URL, DB_USER, DB_PASSWORD));
    }
}