package net.mindengine.testhub.repository.jobs;

public interface JobsRepository {
    Long createJob(String project, String jobName);

    Long createBuild(Long jobId, String buildName);
}
