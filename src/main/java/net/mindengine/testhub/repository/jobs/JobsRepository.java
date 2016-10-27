package net.mindengine.testhub.repository.jobs;

public interface JobsRepository {
    Long createJob(Long projectId, String jobName);

    Long createBuild(Long jobId, String buildName);
}
