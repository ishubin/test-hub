package net.mindengine.testhub.services;

import net.mindengine.testhub.model.files.FileResponse;

public interface FileService {
    FileResponse createFile(String fileName, String storageType, String path, String mediaType, String hash);
}
