package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.tests.Test;
import net.mindengine.testhub.model.tests.TestHistory;
import net.mindengine.testhub.model.tests.TestRequest;
import net.mindengine.testhub.model.tests.TestResponse;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestsApiController extends ApiController {
    private static final int MAX_TEST_HISTORY = 40;
    private final TestsRepository testsRepository;

    public TestsApiController(ProjectsRepository projectRepository, JobsRepository jobsRepository, TestsRepository testsRepository) {
        super(projectRepository, jobsRepository);
        this.testsRepository = testsRepository;
        init();
    }

    private void init() {
        postJson("/api/projects/:project/tests", (req, res) -> {
            String project = req.params("project");
            Long projectId = findMandatoryProject(project);
            TestRequest testRequest = fromJson(req, TestRequest.class);
            Long jobId = jobsRepository.createJob(projectId, testRequest.getJob());
            Long buildId = jobsRepository.createBuild(jobId, testRequest.getBuild());
            Test test = testRequest.asTest(objectMapper);
            test.setBuildId(buildId);
            test.setCreatedDate(new Date());

            List<TestHistory> testHistory = testsRepository.findLastTestHistory(jobId, test.getName(), MAX_TEST_HISTORY);
            test.setAggregatedStatusHistory(objectMapper.writeValueAsString(testHistory));

            return testsRepository.createTest(test);
        });


        getJson("/api/projects/:project/jobs/:job/builds/:build/tests", (req, res) -> {
            String projectName = req.params("project");
            String jobName = req.params("job");
            String buildName = req.params("build");
            String statusFilter = req.queryParams("status");

            Long projectId = findMandatoryProject(projectName);
            Long jobId = findMandatoryJob(projectId, jobName);
            Long buildId = findMandatoryBuild(jobId, buildName);

            return findTestsWithStatus(buildId, statusFilter)
                .stream()
                .map(t -> toTestResponse(jobName, buildName, t)).collect(toList());
        });

    }

    private TestResponse toTestResponse(String jobName, String buildName, Test test) {
        return TestResponse.from(jobName, buildName, test, objectMapper);
    }

    private List<Test> findTestsWithStatus(Long buildId, String statusFilter) {
        if (statusFilter != null) {
            return testsRepository.findTestsByBuildAndStatus(buildId, statusFilter);
        } else {
            return testsRepository.findTestsByBuild(buildId);
        }
    }
}
