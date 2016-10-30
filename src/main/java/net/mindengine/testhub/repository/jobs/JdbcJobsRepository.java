package net.mindengine.testhub.repository.jobs;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.repository.JdbcRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static net.mindengine.testhub.utils.RetryUtils.withRetry;

public class JdbcJobsRepository extends JdbcRepository implements JobsRepository {

    public JdbcJobsRepository(BoneCP masterPool, BoneCP slavePool) {
        super(masterPool, slavePool);
    }

    @Override
    public Long createJob(Long projectId, String jobName) {
        return withRetry(() -> {
            Optional<Long> jobId = findJobIdByProjectAndName(projectId, jobName);
            if (jobId.isPresent()) {
                return jobId.get();
            } else {
                return insert("insert into jobs (project_id, name) values (?, ?)", projectId, jobName);
            }
        });
    }

    @Override
    public Long createBuild(Long jobId, String buildName) {
        return withRetry(() -> {
           Optional<Long> buildId = findBuildByJobAndName(jobId, buildName);
            if (buildId.isPresent()) {
                return buildId.get();
            } else {
                return insert("insert into builds (job_id, name, created_date) values (?, ?, ?)", jobId, buildName, new Date());
            }
        });
    }

    @Override
    public Optional<Long> findBuildByJobAndName(Long jobId, String buildName) {
        return query("select build_id from builds where job_id = ? and name = ?", jobId, buildName).singleLong();
    }

    @Override
    public Optional<Long> findJobIdByProjectAndName(Long projectId, String jobName) {
        return query("select job_id from jobs where project_id = ? and name = ?", projectId, jobName).singleLong();
    }

    @Override
    public List<Job> findAllJobsForProject(Long projectId) {
        return query("select * from jobs where project_id = ?", projectId)
            .list(rs ->
            new Job(rs.getLong("job_id"), rs.getString("name"))
        );
    }

}
