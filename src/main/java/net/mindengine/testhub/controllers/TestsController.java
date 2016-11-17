package net.mindengine.testhub.controllers;

import net.mindengine.testhub.controllers.api.Controller;

public class TestsController extends Controller {

    public TestsController() {
        init();
    }

    private void init() {
        getHsTpl("/projects/:project/jobs/:job/builds/:build/tests", "tests", (req, model) -> {
            model.put("projectName", req.params("project"));
            model.put("jobName", req.params("job"));
            model.put("buildName", req.params("build"));
            model.put("statusFilter", req.queryParams("status"));
        });

        getHsTpl("/projects/:project/jobs/:job/builds/:build/tests/:testId", "single-test", (req, model) -> {
            model.put("projectName", req.params("project"));
            model.put("jobName", req.params("job"));
            model.put("buildName", req.params("build"));
            model.put("testId", req.params("testId"));
        });
    }
}
