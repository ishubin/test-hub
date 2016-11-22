/*******************************************************************************
 * Copyright 2016 Ivan Shubin https://github.com/ishubin/dash-server
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
package net.mindengine.testhub.repository.tests;


import net.mindengine.testhub.model.Attachment;
import net.mindengine.testhub.model.tests.Test;
import net.mindengine.testhub.model.tests.TestHistory;
import net.mindengine.testhub.model.tests.TestStatistics;

import java.util.List;
import java.util.Optional;

public interface TestsRepository {

    Long createTest(Test test);

    List<Test> findTestsByBuildAndStatus(Long buildId, String statusFilter);

    List<Test> findTestsByBuild(Long buildId);

    List<TestHistory> findLastTestHistory(Long jobId, String name, int maxTestHistory);

    TestStatistics countTestStatisticsForBuild(Long buildId);

    void createTestAttachment(Long testId, Attachment attachment);

    Optional<Test> findTestByBuildAndId(Long buildId, Long testId);

    List<Attachment> findTestAttachments(Long testId);
}
