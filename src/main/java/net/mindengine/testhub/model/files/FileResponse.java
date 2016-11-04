package net.mindengine.testhub.model.files;

public class FileResponse {
    private String path;
    private Long fileId;
    private String fileName;
    private String hash;

    public FileResponse(Long fileId, String fileName, String path, String hash) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.path = path;
        this.hash = hash;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
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
