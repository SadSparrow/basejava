package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.basejava.webapp.ResumeTestData.createResume;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:/Users/Ulmon/Desktop/project/forResume");
    protected final Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String UUID_5 = "uuid5";

    private static final Resume R1 = createResume(UUID_1, "C");
    private static final Resume R2 = createResume(UUID_2, "Bb");
    private static final Resume R3 = createResume(UUID_3, "Ba");
    private static final Resume R4 = createResume(UUID_4, "A");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
        storage.save(R4);
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
        storage.save(R4);
    }

    @Test
    public void get() {
        Assert.assertEquals(R1, storage.get(UUID_1));
        Assert.assertEquals(R2, storage.get(UUID_2));
        Assert.assertEquals(R3, storage.get(UUID_3));
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
        List<Resume> expectedResumes = Arrays.asList(R4, R3, R2, R1);
        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}