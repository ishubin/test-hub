/*******************************************************************************
 * Copyright 2016 Ivan Shubin https://github.com/ishubin/test-hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.files.FileInfo;
import net.mindengine.testhub.model.files.FileResponse;
import net.mindengine.testhub.repository.files.FileStorage;
import spark.Request;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;


public class FileApiController extends Controller {
    private final FileStorage fileStorage;
    private final String filesResourceName;

    public FileApiController(FileStorage fileStorage, String filesResourceName) {
        this.fileStorage = fileStorage;
        this.filesResourceName = filesResourceName;
        init();
    }

    private void init() {
        postJson("/api/files", "multipart/form-data", (req, res) -> {
            String fileName = req.queryParams("name");
            String mediaType = req.queryParams("mediaType");
            FileInfo fileInfo = copyImageToStorage(req);
            return new FileResponse(fileName, convertToPublicPath(fileInfo.getPath()), fileInfo.getHash());
        });
    }

    private String convertToPublicPath(String path) {
        return "/"  + filesResourceName + "/" + path.replace("\\", "/");
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
