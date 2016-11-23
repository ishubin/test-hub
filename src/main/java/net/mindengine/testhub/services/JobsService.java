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

import net.mindengine.testhub.model.builds.BuildResponse;
import net.mindengine.testhub.model.jobs.JobResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface JobsService {

    List<JobResponse> findJobs(String project);

    Optional<JobResponse> findJob(String project, String jobName);

    List<BuildResponse> findBuilds(String project, String jobName);

    BuildResponse findBuild(String project, String jobName, String buildName);

    void removeBuildsOlderThan(Date cleanupDate);
}
