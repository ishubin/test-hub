package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.builds.Build;
import net.mindengine.testhub.model.builds.BuildResponse;
import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.jobs.JobResponse;
import net.mindengine.testhub.services.JobsService;

import java.util.Optional;


public class JobsApiController extends Controller {
    private final JobsService jobsService;

    public JobsApiController(JobsService jobsService) {
        this.jobsService = jobsService;
        init();
    }

    private void init() {
        getJson("/api/projects/:project/jobs", (req, res) -> jobsService.findJobs(req.params("project")));

        getJson("/api/projects/:project/jobs/:jobName", (req, res) -> {
            Optional<JobResponse> jobResponse = jobsService.findJob(req.params("project"), req.params("jobName"));
            if (!jobResponse.isPresent()) {
                throw new RuntimeException("Job does not exist");
            } else {
                return jobResponse.get();
            }
        });


        getJson("/api/projects/:project/jobs/:jobName/builds", (req, res) ->
            jobsService.findBuilds(req.params("project"), req.params("jobName"))
        );

        getJson("/api/projects/:project/jobs/:jobName/builds/:buildName", (req, res) ->
            jobsService.findBuild(req.params("project"), req.params("jobName"), req.params("buildName"))
        );
    }

}
