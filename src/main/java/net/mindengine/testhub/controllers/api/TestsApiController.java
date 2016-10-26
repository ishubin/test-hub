package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.tests.TestRequest;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

public class TestsApiController extends ApiController {
    private final TestsRepository testsRepository;
    private final JobsRepository jobsRepository;

    public TestsApiController(JobsRepository jobsRepository, TestsRepository testsRepository) {
        this.jobsRepository = jobsRepository;
        this.testsRepository = testsRepository;
        init();
    }

    private void init() {
        postJson("/api/projects/:project/tests", (req, res) -> {
            String project = req.params("project");
            TestRequest testRequest = fromJson(req, TestRequest.class);
            Long jobId = jobsRepository.createJob(project, testRequest.getJob());
            Long buildId = jobsRepository.createBuild(jobId, testRequest.getBuild());
            return testsRepository.createTest(buildId, testRequest);
        });
    }


}
