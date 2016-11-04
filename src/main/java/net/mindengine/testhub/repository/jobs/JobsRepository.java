package net.mindengine.testhub.repository.jobs;

import net.mindengine.testhub.model.builds.Build;
import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.tests.TestStatistics;

import java.util.List;
import java.util.Optional;

public interface JobsRepository {
    Long createJob(Job job);

    Long createBuild(Long jobId, String buildName);

    Optional<Long> findBuildIdByJobAndName(Long jobId, String buildName);

    Optional<Build> findBuildByJobAndName(Long jobId, String buildName);

    Optional<Long> findJobIdByProjectAndName(Long projectId, String jobName);

    List<Job> findAllJobsForProject(Long projectId);

    Optional<Job> findJobByProjectAndName(Long projectId, String jobName);

    List<Build> findLatestBuildsForJob(Long jobId, int amountOfBuilds);

    void updateTestStatistics(Long buildId, TestStatistics testStatistics);
}
