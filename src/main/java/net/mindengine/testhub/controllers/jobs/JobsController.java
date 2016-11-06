package net.mindengine.testhub.controllers.jobs;

import net.mindengine.testhub.controllers.api.Controller;
import net.mindengine.testhub.services.JobsService;

public class JobsController extends Controller {
    private final JobsService jobsService;

    public JobsController(JobsService jobsService) {
        this.jobsService = jobsService;
        init();
    }

    private void init() {
        getHsTpl("/projects/:project/jobs", "jobs", (req, model) -> {
            model.put("jobs", jobsService.findJobs(req.params("project")));
        });
    }


}
