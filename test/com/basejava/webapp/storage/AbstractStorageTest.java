package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String UUID_5 = "uuid5";

    protected AbstractStorageTest(Storage storage) {
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

    @Test
    public void save() {
        storage.save(new Resume(UUID_5));
        Assert.assertEquals(5, storage.size());
        storage.get(UUID_5);
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
    public void getWrong() {
        storage.get(UUID_5);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_4);
        Assert.assertEquals(3, storage.size());
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteWrong() {
        storage.delete(UUID_5);
    }

    @Test
    public void getAll() {
        Resume[] actualResumes = storage.getAll();
        Resume[] expectedResumes = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3), new Resume(UUID_4)};
        Assert.assertArrayEquals(expectedResumes, actualResumes);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}