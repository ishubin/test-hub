package net.mindengine.testhub.repository.files;

public interface FilesRepository {
    Long createFile(String name, String storageType, String imagePath, String mediaType, String hash);
}
