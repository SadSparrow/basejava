package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveElement(int index, Resume resume) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Comparator<Resume> resumeUuidComparator = Comparator.comparing(Resume::getUuid, String::compareTo);
        Resume searchKey = new Resume(uuid, "A");
        return Arrays.binarySearch(storage, 0, size, searchKey, resumeUuidComparator);
    }
}
