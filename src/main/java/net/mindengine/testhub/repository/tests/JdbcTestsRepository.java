package net.mindengine.testhub.repository.tests;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.model.tests.Test;
import net.mindengine.testhub.repository.JdbcRepository;

public class JdbcTestsRepository extends JdbcRepository implements TestsRepository {
    public JdbcTestsRepository(BoneCP masterPool, BoneCP slavePool) {
        super(masterPool, slavePool);
    }

    @Override
    public Long createTest(Test test) {
        return insert("insert into test_reports " +
            "(build_id, name, error, reason, status, created_date, started_date, ended_date, reported_by, report_type, report, aggregated_status_history)" +
            " values " +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            test.getBuildId(),
            test.getName(),
            test.getError(),
            test.getReason(),
            test.getStatus(),
            test.getCreatedDate(),
            test.getStartedDate(),
            test.getEndedDate(),
            test.getReportedBy(),
            test.getReportType(),
            test.getReport(),
            test.getAggregatedStatusHistory());
    }
}
