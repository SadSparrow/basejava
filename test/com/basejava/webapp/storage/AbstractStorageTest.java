package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.basejava.webapp.ResumeTestData.createResume;

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
        storage.save(createResume(UUID_1, "C"));
        storage.save(createResume(UUID_2, "Bb"));
        storage.save(createResume(UUID_3, "Ba"));
        storage.save(createResume(UUID_4, "A"));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume r = createResume(UUID_4, "new A");
        storage.update(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateWrong() {
        Resume r = createResume(UUID_5, "Dd");
        storage.update(r);
    }

    @Test
    public void save() {
        storage.save(createResume(UUID_5, "Dd"));
        Assert.assertEquals(5, storage.size());
        storage.get(UUID_5);
    }

    @Test(expected = ExistStorageException.class)
    public void saveWrong() {
        storage.save(createResume(UUID_4, "A"));
    }

    @Test
    public void get() {
        Resume r = createResume(UUID_1, "C");
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
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(
                createResume(UUID_4, "A"),
                createResume(UUID_3, "Ba"),
                createResume(UUID_2, "Bb"),
                createResume(UUID_1, "C"));
        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}