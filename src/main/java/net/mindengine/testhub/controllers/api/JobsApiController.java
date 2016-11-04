package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.builds.Build;
import net.mindengine.testhub.model.builds.BuildResponse;
import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.jobs.JobResponse;
import net.mindengine.testhub.repository.RepositoryProvider;

import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class JobsApiController extends Controller {

    private static final int AMOUNT_OF_BUILDS = 10;

    public JobsApiController(RepositoryProvider repositoryProvider) {
        super(repositoryProvider);
        init();
    }

    private void init() {
        getJson("/api/projects/:project/jobs", (req, res) -> {
            Long projectId = findMandatoryProject(req.params("project"));
            return jobs().findAllJobsForProject(projectId).stream()
                .map(j ->
                    new JobResponse(j.getName())
                ).collect(toList());
        });

        getJson("/api/projects/:project/jobs/:jobName", (req, res) -> {
            String jobName = req.params("jobName");
            Long projectId = findMandatoryProject(req.params("project"));
            Optional<Job> job = jobs().findJobByProjectAndName(projectId, jobName);
            if (job.isPresent()) {
                return new JobResponse(job.get().getName());
            } else {
                throw new RuntimeException("Job does not exist");
            }
        });


        getJson("/api/projects/:project/jobs/:jobName/builds", (req, res) -> {
            Long projectId = findMandatoryProject(req.params("project"));
            Long jobId = findMandatoryJob(projectId, req.params("jobName"));
            return jobs().findLatestBuildsForJob(jobId, AMOUNT_OF_BUILDS).stream()
                .map(b -> new BuildResponse(b.getName()))
                .collect(toList());
        });

        getJson("/api/projects/:project/jobs/:jobName/builds/:buildName", (req, res) -> {
            Long projectId = findMandatoryProject(req.params("project"));
            Long jobId = findMandatoryJob(projectId, req.params("jobName"));
            String buildName = req.params("buildName");
            Optional<Build> build = jobs().findBuildByJobAndName(jobId, buildName);
            if (!build.isPresent()) {
                throw new RuntimeException("Build does not exist: " + buildName);
            }
            return build.get();
        });
    }

}
