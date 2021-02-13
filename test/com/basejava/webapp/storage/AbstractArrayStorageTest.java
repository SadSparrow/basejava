package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume("uuid4"));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume r = new Resume("uuid4");
        storage.update(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateWrong() {
        Resume r = new Resume("uuid5");
        storage.update(r);
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid5"));
        Assert.assertEquals(5, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveWrong() {
        storage.save(new Resume("uuid4"));
    }

    @Test
    public void get() {
        Resume r = new Resume("uuid1");
        Assert.assertEquals(r, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        storage.delete("uuid4");
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteWrong() {
        storage.delete("dummy");

    }

    @Test
    public void getAll() {
        Resume[] storageCopy = storage.getAll();
        Assert.assertEquals(storage.size(), storageCopy.length);
        for (int i = 0; i < storage.size(); i++) {
            Assert.assertEquals(storage.get("uuid" + (i + 1)), storageCopy[i]);
        }
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void overflow() {
        try {
            for (int i = storage.size() + 1; i <= 10_000; i++) {
                storage.save(new Resume("uuid" + i));

            }
        } catch (StorageException e) {
            Assert.fail("overflow occurred ahead of time".toUpperCase(Locale.ROOT));
        }
        storage.save(new Resume("uuidMAX"));
    }
}