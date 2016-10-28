package net.mindengine.testhub.repository.tests;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.model.tests.Test;
import net.mindengine.testhub.model.tests.TestExtendedStatus;
import net.mindengine.testhub.repository.JdbcRepository;

import java.util.List;

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

    @Override
    public List<TestExtendedStatus> findLastTestHistory(Long jobId, String name, int amountOfTests) {
        return query("select tr.test_report_id, tr.status, tr.reason from test_reports tr " +
            "left join builds b on b.build_id = tr.build_id " +
            "where b.job_id = ? and tr.name = ?" +
            "order by test_report_id desc limit 0, 40",
            jobId, name)
            .list(rs -> new TestExtendedStatus(
                rs.getLong("test_report_id"),
                rs.getString("status"),
                rs.getString("reason")
            ));
    }
}
