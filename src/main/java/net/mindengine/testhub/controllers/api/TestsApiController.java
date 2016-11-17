package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.tests.*;
import net.mindengine.testhub.services.TestService;

import java.util.Optional;

public class TestsApiController extends Controller {
    private final TestService testService;

    public TestsApiController(TestService testService) {
        this.testService = testService;
        init();
    }

    private void init() {
        postJson("/api/projects/:project/tests", (req, res) ->
            testService.createTest(req.params("project"), fromJson(req, TestRequest.class))
        );

        getJson("/api/projects/:project/jobs/:job/builds/:build/tests", (req, res) -> testService.findTests(
            req.params("project"),
            req.params("job"),
            req.params("build"),
            Optional.ofNullable(req.queryParams("status"))
        ));

        getJson("/api/projects/:project/jobs/:job/builds/:build/tests/:testId", (req, res) -> testService.findTest(
            req.params("project"),
            req.params("job"),
            req.params("build"),
            Long.parseLong(req.params("testId"))
        ));
    }

}
