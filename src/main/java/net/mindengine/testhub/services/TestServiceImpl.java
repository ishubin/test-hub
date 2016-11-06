package net.mindengine.testhub.services;

import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.tests.*;
import net.mindengine.testhub.repository.RepositoryProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class TestServiceImpl extends ServiceImpl implements TestService {

    private static final int MAX_TEST_HISTORY = 40;
    private static final Long NO_JOB_ID = null;

    public TestServiceImpl(RepositoryProvider repositoryProvider) {
        super(repositoryProvider);
    }

    @Override
    public TestResponse createTest(String project, TestRequest testRequest) {
        Long projectId = findMandatoryProject(project);

        try {
            Long jobId = jobs().createJob(new Job(NO_JOB_ID, projectId, testRequest.getJob()));
            Long buildId = jobs().createBuild(jobId, testRequest.getBuild());
            Test test = testRequest.asTest(objectMapper);
            test.setBuildId(buildId);
            test.setCreatedDate(new Date());

            List<TestHistory> testHistory = tests().findLastTestHistory(jobId, test.getName(), MAX_TEST_HISTORY);
            test.setAggregatedStatusHistory(objectMapper.writeValueAsString(testHistory));

            tests().createTest(test);

            updateBuildStatistics(buildId);

            return TestResponse.from(testRequest.getJob(), testRequest.getBuild(), test, objectMapper);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<TestResponse> findTests(String projectName, String jobName, String buildName, Optional<String> expectedStatus) {
        Long projectId = findMandatoryProject(projectName);
        Long jobId = findMandatoryJob(projectId, jobName);
        Long buildId = findMandatoryBuild(jobId, buildName);

        return findTestsWithStatus(buildId, expectedStatus)
            .stream()
            .map(t -> toTestResponse(jobName, buildName, t)).collect(toList());
    }

    private void updateBuildStatistics(Long buildId) {
        TestStatistics statistics = tests().countTestStatisticsForBuild(buildId);
        jobs().updateTestStatistics(buildId, statistics);
    }

    private TestResponse toTestResponse(String jobName, String buildName, Test test) {
        return TestResponse.from(jobName, buildName, test, objectMapper);
    }

    private List<Test> findTestsWithStatus(Long buildId, Optional<String> statusFilter) {
        if (statusFilter.isPresent()) {
            return tests().findTestsByBuildAndStatus(buildId, statusFilter.get());
        } else {
            return tests().findTestsByBuild(buildId);
        }
    }
}
