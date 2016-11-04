package net.mindengine.testhub.repository.tests;


import net.mindengine.testhub.model.tests.Test;
import net.mindengine.testhub.model.tests.TestHistory;
import net.mindengine.testhub.model.tests.TestStatistics;

import java.util.List;

public interface TestsRepository {

    Long createTest(Test test);

    List<Test> findTestsByBuildAndStatus(Long buildId, String statusFilter);

    List<Test> findTestsByBuild(Long buildId);

    List<TestHistory> findLastTestHistory(Long jobId, String name, int maxTestHistory);

    TestStatistics countTestStatisticsForBuild(Long buildId);
}
