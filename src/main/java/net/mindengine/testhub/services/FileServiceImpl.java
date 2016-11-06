package net.mindengine.testhub.services;

import net.mindengine.testhub.model.files.FileResponse;
import net.mindengine.testhub.repository.RepositoryProvider;

public class FileServiceImpl extends ServiceImpl implements FileService {
    public FileServiceImpl(RepositoryProvider repositoryProvider) {
        super(repositoryProvider);
    }

    @Override
    public FileResponse createFile(String fileName, String storageType, String path, String mediaType, String hash) {
        Long fileId = files().createFile(fileName, storageType, path, mediaType, hash);
        return new FileResponse(Long.toString(fileId), fileName, path, hash);
    }
}
