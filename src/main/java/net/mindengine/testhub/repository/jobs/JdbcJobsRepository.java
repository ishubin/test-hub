package net.mindengine.testhub.repository.jobs;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.repository.JdbcRepository;
import java.util.Date;
import java.util.Optional;

import static net.mindengine.testhub.utils.RetryUtils.withRetry;

public class JdbcJobsRepository extends JdbcRepository implements JobsRepository {

    public JdbcJobsRepository(BoneCP masterPool, BoneCP slavePool) {
        super(masterPool, slavePool);
    }

    @Override
    public Long createJob(String project, String jobName) {
        return withRetry(() -> {
            Optional<Long> jobId = query("select job_id from jobs where name = ?", jobName).singleLong();
            if (jobId.isPresent()) {
                return jobId.get();
            } else {
                return insert("insert into jobs (name) values (?)", jobName);
            }
        });
    }

    @Override
    public Long createBuild(Long jobId, String buildName) {
        return withRetry(() -> {
           Optional<Long> buildId = query("select build_id where job_id = ? and name = ?", jobId, buildName).singleLong();
            if (buildId.isPresent()) {
                return buildId.get();
            } else {
                return insert("insert into builds (jobId, name, created_date) values (?, ?, ?)", jobId, buildName, new Date());
            }
        });
    }

}
