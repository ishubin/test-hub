package net.mindengine.testhub.services;

import net.mindengine.testhub.model.tests.TestRequest;
import net.mindengine.testhub.model.tests.TestResponse;

import java.util.List;
import java.util.Optional;

public interface TestService {

    TestResponse createTest(String project, TestRequest testRequest);

    List<TestResponse> findTests(String projectName, String jobName, String buildName, Optional<String> expectedStatus);

    TestResponse findTest(String projectName, String jobName, String buildName, Long testId);
}
