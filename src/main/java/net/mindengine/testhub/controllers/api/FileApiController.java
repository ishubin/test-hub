package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.files.FileInfo;
import net.mindengine.testhub.model.files.FileResponse;
import net.mindengine.testhub.repository.RepositoryProvider;
import net.mindengine.testhub.repository.files.FileStorage;
import spark.Request;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;


public class FileApiController extends Controller {
    private final FileStorage fileStorage;

    public FileApiController(RepositoryProvider repositoryProvider, FileStorage fileStorage) {
        super(repositoryProvider);
        this.fileStorage = fileStorage;
        init();
    }

    private void init() {
        postJson("/api/files", "multipart/form-data", (req, res) -> {
            String fileName = req.queryParams("name");
            String mediaType = req.queryParams("mediaType");
            FileInfo fileInfo = copyImageToStorage(req);
            Long fileId = files().createFile(fileName, fileStorage.getStorageType(), fileInfo.getPath(), mediaType, fileInfo.getHash());
            return new FileResponse(fileId, fileName, fileInfo.getPath(), fileInfo.getHash());
        });
    }


    private FileInfo copyImageToStorage(Request req) throws IOException, ServletException {
        if (req.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        }

        Part file = req.raw().getPart("file");
        FileInfo imageInfo = fileStorage.saveImageToStorage(file.getInputStream());
        file.delete();
        return imageInfo;
    }


}
