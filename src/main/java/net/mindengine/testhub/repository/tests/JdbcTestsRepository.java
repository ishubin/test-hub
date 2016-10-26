package net.mindengine.testhub.repository.tests;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.model.tests.TestRequest;
import net.mindengine.testhub.repository.JdbcRepository;

public class JdbcTestsRepository extends JdbcRepository implements TestsRepository {
    public JdbcTestsRepository(BoneCP masterPool, BoneCP slavePool) {
        super(masterPool, slavePool);
    }

    @Override
    public Long createTest(Long buildId, TestRequest testRequest) {
        throw new RuntimeException("not finished yet");
    }
}
