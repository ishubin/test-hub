/*******************************************************************************
 * Copyright 2016 Ivan Shubin https://github.com/ishubin/test-hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.mindengine.testhub.services;

import net.mindengine.testhub.model.Attachment;
import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.tests.*;
import net.mindengine.testhub.repository.RepositoryProvider;

import java.util.Date;
import java.util.LinkedList;
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

            Long testId = tests().createTest(test);

            List<Attachment> savedAttachments = new LinkedList<>();
            if (testRequest.getAttachments() != null) {
                testRequest.getAttachments().stream().forEach(attachment -> {
                    try {
                        tests().createTestAttachment(testId, attachment);
                        savedAttachments.add(attachment);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            updateBuildStatistics(buildId);

            TestResponse testResponse = TestResponse.from(testRequest.getJob(), testRequest.getBuild(), test, savedAttachments, objectMapper);
            testResponse.setTestId(testId);
            return testResponse;
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

    @Override
    public TestResponse findTest(String projectName, String jobName, String buildName, Long testId) {
        Long projectId = findMandatoryProject(projectName);
        Long jobId = findMandatoryJob(projectId, jobName);
        Long buildId = findMandatoryBuild(jobId, buildName);

        return findTestWithAllData(buildId, testId, jobName, buildName);
    }

    private TestResponse findTestWithAllData(Long buildId, Long testId, String jobName, String buildName) {
        Optional<Test> test = tests().findTestByBuildAndId(buildId, testId);
        if (test.isPresent()) {
            List<Attachment> attachments = tests().findTestAttachments(test.get().getTestId());
            return TestResponse.from(jobName, buildName, test.get(), attachments, objectMapper);
        } else {
            throw new RuntimeException("Test doesn't not exist: " + testId);
        }
    }

    private void updateBuildStatistics(Long buildId) {
        TestStatistics statistics = tests().countTestStatisticsForBuild(buildId);
        jobs().updateTestStatistics(buildId, statistics);
    }

    private TestResponse toTestResponse(String jobName, String buildName, Test test) {
        return TestResponse.from(jobName, buildName, test, null, objectMapper);
    }

    private List<Test> findTestsWithStatus(Long buildId, Optional<String> statusFilter) {
        if (statusFilter.isPresent()) {
            return tests().findTestsByBuildAndStatus(buildId, statusFilter.get());
        } else {
            return tests().findTestsByBuild(buildId);
        }
    }
}
