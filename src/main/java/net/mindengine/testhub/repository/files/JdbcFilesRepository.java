package net.mindengine.testhub.repository.files;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.repository.JdbcRepository;

import java.util.Date;

public class JdbcFilesRepository extends JdbcRepository implements FilesRepository {
    public JdbcFilesRepository(BoneCP masterPool, BoneCP slavePool) {
        super(masterPool, slavePool);
    }

    @Override
    public Long createFile(String name, String storageType, String imagePath, String mediaType, String hash) {
        return insert("insert into files (name, storage_type, image_path, media_type, hash, created_date) values (?, ?, ?, ?, ?, ?)",
            name, storageType, imagePath, mediaType, hash, new Date()
        );
    }
}
