package net.mindengine.testhub.repository.tests;


import net.mindengine.testhub.model.tests.Test;
import net.mindengine.testhub.model.tests.TestExtendedStatus;

import java.util.List;

public interface TestsRepository {

    Long createTest(Test test);


    List<TestExtendedStatus> findLastTestHistory(Long jobId, String name, int amountOfTests);
}
