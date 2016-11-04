package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.tests.*;
import net.mindengine.testhub.repository.RepositoryProvider;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestsApiController extends Controller {
    private static final int MAX_TEST_HISTORY = 40;
    private static final Long NO_JOB_ID = null;

    public TestsApiController(RepositoryProvider repositoryProvider) {
        super(repositoryProvider);
        init();
    }

    private void init() {
        postJson("/api/projects/:project/tests", (req, res) -> {
            String project = req.params("project");
            Long projectId = findMandatoryProject(project);
            TestRequest testRequest = fromJson(req, TestRequest.class);


            Long jobId = jobs().createJob(new Job(NO_JOB_ID, projectId, testRequest.getJob()));
            Long buildId = jobs().createBuild(jobId, testRequest.getBuild());
            Test test = testRequest.asTest(objectMapper);
            test.setBuildId(buildId);
            test.setCreatedDate(new Date());

            List<TestHistory> testHistory = tests().findLastTestHistory(jobId, test.getName(), MAX_TEST_HISTORY);
            test.setAggregatedStatusHistory(objectMapper.writeValueAsString(testHistory));

            Long testId = tests().createTest(test);

            updateBuildStatistics(buildId);
            return testId;
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

    private void updateBuildStatistics(Long buildId) {
        TestStatistics statistics = tests().countTestStatisticsForBuild(buildId);
        jobs().updateTestStatistics(buildId, statistics);
    }

    private TestResponse toTestResponse(String jobName, String buildName, Test test) {
        return TestResponse.from(jobName, buildName, test, objectMapper);
    }

    private List<Test> findTestsWithStatus(Long buildId, String statusFilter) {
        if (statusFilter != null) {
            return tests().findTestsByBuildAndStatus(buildId, statusFilter);
        } else {
            return tests().findTestsByBuild(buildId);
        }
    }
}
