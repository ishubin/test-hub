package net.mindengine.testhub.model.files;

public class FileResponse {
    private String path;
    private String fileName;
    private String hash;

    public FileResponse(String fileName, String path, String hash) {
        this.fileName = fileName;
        this.path = path;
        this.hash = hash;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
