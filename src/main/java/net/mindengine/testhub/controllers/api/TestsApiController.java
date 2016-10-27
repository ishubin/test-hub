package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.tests.Test;
import net.mindengine.testhub.model.tests.TestRequest;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

import java.util.Date;
import java.util.Optional;

public class TestsApiController extends ApiController {
    private final TestsRepository testsRepository;
    private final JobsRepository jobsRepository;
    private final ProjectsRepository projectRepository;

    public TestsApiController(ProjectsRepository projectRepository, JobsRepository jobsRepository, TestsRepository testsRepository) {
        this.projectRepository = projectRepository;
        this.jobsRepository = jobsRepository;
        this.testsRepository = testsRepository;
        init();
    }

    private void init() {
        postJson("/api/projects/:project/tests", (req, res) -> {
            String project = req.params("project");
            Optional<Long> projectId = projectRepository.findProjectIdByName(project);
            if (!projectId.isPresent()) {
                throw new RuntimeException("Project does not exist: " + project);
            }

            TestRequest testRequest = fromJson(req, TestRequest.class);
            Long jobId = jobsRepository.createJob(projectId.get(), testRequest.getJob());
            Long buildId = jobsRepository.createBuild(jobId, testRequest.getBuild());
            Test test = testRequest.asTest(objectMapper);
            test.setBuildId(buildId);
            test.setCreatedDate(new Date());
            return testsRepository.createTest(test);
        });
    }


}
