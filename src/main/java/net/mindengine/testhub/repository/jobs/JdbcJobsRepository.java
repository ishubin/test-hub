package net.mindengine.testhub.repository.jobs;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.model.builds.Build;
import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.tests.TestStatistics;
import net.mindengine.testhub.repository.JdbcRepository;
import net.mindengine.testhub.repository.ResultSetMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static net.mindengine.testhub.utils.RetryUtils.withRetry;

public class JdbcJobsRepository extends JdbcRepository implements JobsRepository {

    private ResultSetMapper.RSFunction<Job> jobReader = (rs) ->
        new Job(rs.getLong("job_id"), rs.getString("name"));
    private ResultSetMapper.RSFunction<Build> buildReader = (rs) ->
        new Build(
            rs.getLong("build_id"),
            rs.getLong("job_id"),
            rs.getString("name"),
            rs.getLong("aggr_cnt_tests_passed"),
            rs.getLong("aggr_cnt_tests_failed"),
            rs.getLong("aggr_cnt_tests_skipped")
        );

    public JdbcJobsRepository(BoneCP masterPool, BoneCP slavePool) {
        super(masterPool, slavePool);
    }

    @Override
    public Long createJob(Job job) {
        return withRetry(() -> {
            Optional<Long> jobId = findJobIdByProjectAndName(job.getProjectId(), job.getName());
            if (jobId.isPresent()) {
                return jobId.get();
            } else {
                return insert("insert into jobs (project_id, name) values (?, ?)", job.getProjectId(), job.getName());
            }
        });
    }

    @Override
    public Long createBuild(Long jobId, String buildName) {
        return withRetry(() -> {
           Optional<Long> buildId = findBuildIdByJobAndName(jobId, buildName);
            if (buildId.isPresent()) {
                return buildId.get();
            } else {
                return insert("insert into builds (job_id, name, created_date, status) values (?, ?, ?, 'passed')",
                    jobId, buildName, new Date());
            }
        });
    }

    @Override
    public Optional<Long> findBuildIdByJobAndName(Long jobId, String buildName) {
        return query("select build_id from builds where job_id = ? and name = ? limit 0, 1", jobId, buildName).singleLong();
    }

    @Override
    public Optional<Build> findBuildByJobAndName(Long jobId, String buildName) {
        return query("select * from builds where job_id = ? and name = ? limit 0, 1", jobId, buildName).single(buildReader);
    }

    @Override
    public Optional<Long> findJobIdByProjectAndName(Long projectId, String jobName) {
        return query("select job_id from jobs where project_id = ? and name = ? limit 0, 1", projectId, jobName).singleLong();
    }

    @Override
    public List<Job> findAllJobsForProject(Long projectId) {
        return query("select * from jobs where project_id = ?", projectId)
            .list(jobReader);
    }

    @Override
    public Optional<Job> findJobByProjectAndName(Long projectId, String jobName) {
        return query("select * from jobs where project_id = ? and name = ? limit 0, 1", projectId, jobName)
            .single(jobReader);
    }

    @Override
    public List<Build> findLatestBuildsForJob(Long jobId, int amountOfBuilds) {
        return query("select * from builds where job_id = ? limit 0, ?", jobId, amountOfBuilds)
            .list(buildReader);
    }

    @Override
    public void updateTestStatistics(Long buildId, TestStatistics testStatistics) {
        update("update builds set " +
            "aggr_cnt_tests_passed = ?, " +
            "aggr_cnt_tests_failed = ?, " +
            "aggr_cnt_tests_skipped = ? " +
            "where build_id = ?",
            testStatistics.getPassed(),
            testStatistics.getFailed(),
            testStatistics.getSkipped(),
            buildId);
    }

}
