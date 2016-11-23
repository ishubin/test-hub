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

import net.mindengine.testhub.model.builds.Build;
import net.mindengine.testhub.model.builds.BuildResponse;
import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.jobs.JobResponse;
import net.mindengine.testhub.model.tests.TestStatus;
import net.mindengine.testhub.repository.RepositoryProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class JobsServiceImpl extends ServiceImpl implements JobsService {
    private static final int AMOUNT_OF_BUILDS = 10;
    private static final int ONE_BUILD = 1;

    public JobsServiceImpl(RepositoryProvider repositoryProvider) {
        super(repositoryProvider);
    }

    @Override
    public List<JobResponse> findJobs(String project) {
        Long projectId = findMandatoryProject(project);
        return findJobsWithLatestBuild(projectId);
    }

    @Override
    public Optional<JobResponse> findJob(String project, String jobName) {
        Long projectId = findMandatoryProject(project);
        Optional<Job> job = jobs().findJobByProjectAndName(projectId, jobName);
        if (job.isPresent()) {
            Build build = findLatestBuild(job.get());
            return Optional.of(new JobResponse(job.get().getName(), BuildResponse.from(build), provideJobStatus(build)));
        } else {
            throw new RuntimeException("Job does not exist");
        }
    }

    @Override
    public List<BuildResponse> findBuilds(String project, String jobName) {
        Long projectId = findMandatoryProject(project);
        Long jobId = findMandatoryJob(projectId, jobName);
        return jobs().findLatestBuildsForJob(jobId, AMOUNT_OF_BUILDS).stream()
            .map(BuildResponse::from)
            .collect(toList());

    }

    @Override
    public BuildResponse findBuild(String project, String jobName, String buildName) {
        Long projectId = findMandatoryProject(project);
        Long jobId = findMandatoryJob(projectId, jobName);
        Optional<BuildResponse> build = jobs().findBuildByJobAndName(jobId, buildName).map(BuildResponse::from);
        if (!build.isPresent()) {
            throw new RuntimeException("Build does not exist: " + buildName);
        }
        return build.get();

    }

    @Override
    public void removeBuildsOlderThan(Date cleanupDate) {
        jobs().removeBuildsOlderThan(cleanupDate);
    }

    private List<JobResponse> findJobsWithLatestBuild(Long projectId) {
        return jobs().findAllJobsForProject(projectId).stream()
            .map(job -> {
                Build build = findLatestBuild(job);
                return new JobResponse(job.getName(), BuildResponse.from(build), provideJobStatus(build));
            })
            .filter(job -> job.getLatestBuild() != null)
            .collect(toList());
    }

    private TestStatus provideJobStatus(Build build) {
        if (build != null) {
            if (build.getCntTestsFailed() > 0) {
                return TestStatus.failed;
            }
        }

        return TestStatus.passed;
    }

    private Build findLatestBuild(Job job) {
        return jobs().findLatestBuildsForJob(job.getJobId(), ONE_BUILD).stream().findFirst().orElse(null);
    }
}
