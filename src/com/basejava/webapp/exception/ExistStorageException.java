package com.basejava.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String message, String uuid) {
        super(message + " ERROR: Resume " + uuid + " already exist", uuid);
    }
}
