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
package net.mindengine.testhub.repository.files;

import net.mindengine.testhub.model.files.FileInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class LocalFileStorage implements FileStorage {

    private final File root;

    public LocalFileStorage(String storagePath) {
        root = new File(storagePath);
        if (!root.exists()) {
            if (!root.mkdirs()) {
                throw new RuntimeException("Couldn't create storage directory: " + storagePath);
            }
        }

        if (!root.isDirectory()) {
            throw new RuntimeException("Not a directory: " + storagePath);
        }
    }

    @Override
    public byte[] readFile(String imagePath) {
        try {
            File file = new File(root.getPath() + File.separator + imagePath);
            if (file.exists()) {
                return FileUtils.readFileToByteArray(file);
            } else {
                throw new FileNotFoundException(file.getPath());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Can't read file: " + imagePath, ex);
        }
    }

    @Override
    public FileInfo saveImageToStorage(InputStream inputStream) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            String dirsPath = generateImageDirsPath();

            File dirs = new File(root.getPath() + File.separator + dirsPath);
            dirs.mkdirs();

            String fileName = UUID.randomUUID().toString();
            File file = new File(dirs.getPath() + File.separator + fileName);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);

            DigestInputStream dis = new DigestInputStream(inputStream, md);

            IOUtils.copy(dis, fos);
            fos.flush();
            fos.close();

            byte[] digest = md.digest();
            String hash = new String(Base64.getEncoder().encode(digest));
            return new FileInfo(hash, dirsPath + File.separator + fileName);
        } catch (Exception ex) {
            throw new RuntimeException("Cannot save image to storage", ex);
        }
    }

    @Override
    public String getStorageType() {
        return "local";
    }

    public String generateImageDirsPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH");
        return sdf.format(new Date()).replace("/", File.separator);
    }
}
