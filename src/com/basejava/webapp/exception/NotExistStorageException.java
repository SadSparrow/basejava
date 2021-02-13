package com.basejava.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String message, String uuid) {
        super(message + " ERROR: Resume " + uuid + " not exist", uuid);
    }
}
