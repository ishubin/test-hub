package net.mindengine.testhub.repository.tests;

import net.mindengine.testhub.model.tests.TestRequest;

public interface TestsRepository {

    Long createTest(Long buildId, TestRequest testRequest);
}
