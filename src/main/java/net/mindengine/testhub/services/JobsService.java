package net.mindengine.testhub.services;

import net.mindengine.testhub.model.builds.BuildResponse;
import net.mindengine.testhub.model.jobs.JobResponse;

import java.util.List;
import java.util.Optional;

public interface JobsService {

    List<JobResponse> findJobs(String project);

    Optional<JobResponse> findJob(String project, String jobName);

    List<BuildResponse> findBuilds(String project, String jobName);

    BuildResponse findBuild(String project, String jobName, String buildName);
}
