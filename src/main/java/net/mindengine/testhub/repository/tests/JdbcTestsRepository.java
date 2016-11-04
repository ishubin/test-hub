package net.mindengine.testhub.repository.tests;

import com.jolbox.bonecp.BoneCP;
import javafx.util.Pair;
import net.mindengine.testhub.model.tests.Test;
import net.mindengine.testhub.model.tests.TestHistory;
import net.mindengine.testhub.model.tests.TestStatistics;
import net.mindengine.testhub.model.tests.TestStatus;
import net.mindengine.testhub.repository.JdbcRepository;
import net.mindengine.testhub.repository.ResultSetMapper;
import java.util.List;

public class JdbcTestsRepository extends JdbcRepository implements TestsRepository {
    private ResultSetMapper.RSFunction<Test> testsReader = rs -> {
        Test test = new Test();
        test.setTestId(rs.getLong("test_report_id"));
        test.setName(rs.getString("name"));
        test.setStatus(TestStatus.valueOf(rs.getString("status")));
        test.setReason(rs.getString("reason"));
        test.setError(rs.getString("error"));
        test.setReportedBy(rs.getString("reported_by"));
        test.setCreatedDate(rs.getDate("created_date"));
        test.setStartedDate(rs.getDate("started_date"));
        test.setEndedDate(rs.getDate("ended_date"));
        test.setAggregatedStatusHistory(rs.getString("aggregated_status_history"));
        test.setReportType(rs.getString("report_type"));
        test.setReport(rs.getString("report"));
        return test;
    };

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
            test.getStatus().name(),
            test.getCreatedDate(),
            test.getStartedDate(),
            test.getEndedDate(),
            test.getReportedBy(),
            test.getReportType(),
            test.getReport(),
            test.getAggregatedStatusHistory());
    }

    public List<Test> findTestsByBuildAndStatus(Long buildId, String statusFilter) {
        return query("select * from test_reports where build_id = ? and status = ?", buildId, statusFilter).list(testsReader);
    }

    @Override
    public List<Test> findTestsByBuild(Long buildId) {
        return query("select * from test_reports where build_id = ?", buildId).list(testsReader);
    }

    @Override
    public List<TestHistory> findLastTestHistory(Long jobId, String name, int maxTestHistory) {
        return query("select tr.test_report_id, tr.status, tr.reason, b.name from test_reports tr " +
            "left join builds b on b.build_id = tr.build_id " +
            "where tr.name = ? and b.job_id = ? " +
            "order by tr.test_report_id desc " +
            "limit 0, ?",
            name, jobId, maxTestHistory
        ).list(rs -> new TestHistory(
            rs.getLong("test_report_id"),
            rs.getString("name"),
            rs.getString("status"),
            rs.getString("reason")
        ));
    }

    @Override
    public TestStatistics countTestStatisticsForBuild(Long buildId) {
        List<Pair<TestStatus, Long>> list = query("select status, count(*) as cnt from test_reports where build_id = ? group by status", buildId).list(rs ->
            new Pair<>(TestStatus.valueOf(rs.getString("status")), rs.getLong("cnt"))
        );

        TestStatistics statistics = new TestStatistics();
        list.stream().forEach(s -> {
            switch (s.getKey()) {
                case passed: {statistics.setPassed(s.getValue()); break;}
                case failed: {statistics.setFailed(s.getValue()); break;}
                case skipped: {statistics.setSkipped(s.getValue()); break;}

            }
        });
        return statistics;
    }
}
