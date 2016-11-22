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
package net.mindengine.testhub.repository.jobs;

import net.mindengine.testhub.model.builds.Build;
import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.tests.TestStatistics;

import java.util.List;
import java.util.Optional;

public interface JobsRepository {
    Long createJob(Job job);

    Long createBuild(Long jobId, String buildName);

    Optional<Long> findBuildIdByJobAndName(Long jobId, String buildName);

    Optional<Build> findBuildByJobAndName(Long jobId, String buildName);

    Optional<Long> findJobIdByProjectAndName(Long projectId, String jobName);

    List<Job> findAllJobsForProject(Long projectId);

    Optional<Job> findJobByProjectAndName(Long projectId, String jobName);

    List<Build> findLatestBuildsForJob(Long jobId, int amountOfBuilds);

    void updateTestStatistics(Long buildId, TestStatistics testStatistics);
}
