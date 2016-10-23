package net.mindengine.testhub.model.files;

public class FileInfo {
    private final String hash;
    private final String path;

    public FileInfo(String hash, String path) {
        this.hash = hash;
        this.path = path;
    }

    public String getHash() {
        return hash;
    }

    public String getPath() {
        return path;
    }
}
