package net.mindengine.testhub.repository.jobs;

import java.util.Optional;

public interface JobsRepository {
    Long createJob(Long projectId, String jobName);

    Long createBuild(Long jobId, String buildName);

    Optional<Long> findBuildByJobAndName(Long jobId, String buildName);

    Optional<Long> findJobIdByProjectAndName(Long projectId, String jobName);
}
