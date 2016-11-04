package net.mindengine.testhub.repository.files;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.repository.JdbcRepository;

public class JdbcFilesRepository extends JdbcRepository implements FilesRepository {
    public JdbcFilesRepository(BoneCP masterPool, BoneCP slavePool) {
        super(masterPool, slavePool);
    }

    @Override
    public Long createFile(String name, String storageType, String imagePath, String mediaType, String hash) {
        return insert("insert into files (name, storage_type, image_path, media_type, hash) values (?, ?, ?, ?, ?)",
            name, storageType, imagePath, mediaType, hash
        );
    }
}
