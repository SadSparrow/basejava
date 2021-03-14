package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;
import java.util.logging.Level;

import static com.basejava.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static com.basejava.webapp.storage.AbstractStorage.LOG;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void overflow() {
        LOG.setLevel(Level.WARNING);
        try {
            for (int i = storage.size() + 1; i <= STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i, "null"));
            }
        } catch (StorageException e) {
            Assert.fail("overflow occurred ahead of time".toUpperCase(Locale.ROOT));
        }
        storage.save(new Resume("uuidMAX", "NameMax"));
    }
}