package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static com.basejava.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_4));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_4);
        storage.update(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateWrong() {
        Resume r = new Resume(UUID_5);
        storage.update(r);
    }

    @Test(expected = ExistStorageException.class)
    public void save() {
        try {
            storage.save(new Resume(UUID_5));
        } catch (ExistStorageException e) {
            Assert.fail("This uuid not exist");
        }
        Assert.assertEquals(5, storage.size());
        storage.save(new Resume(UUID_5));
    }

    @Test(expected = ExistStorageException.class)
    public void saveWrong() {
        storage.save(new Resume(UUID_4));
    }

    @Test
    public void get() {
        Resume r = new Resume(UUID_1);
        Assert.assertEquals(r, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        try {
            storage.delete(UUID_4);
        } catch (NotExistStorageException e) {
            Assert.fail("This uuid exist");
        }
        Assert.assertEquals(3, storage.size());
        storage.delete(UUID_4);
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
            for (int i = storage.size() + 1; i <= STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));

            }
        } catch (StorageException e) {
            Assert.fail("overflow occurred ahead of time".toUpperCase(Locale.ROOT));
        }
        storage.save(new Resume("uuidMAX"));
    }
}