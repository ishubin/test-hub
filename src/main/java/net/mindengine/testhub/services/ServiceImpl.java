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

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mindengine.testhub.repository.RepositoryProvider;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class ServiceImpl {
    private final RepositoryProvider repositoryProvider;
    ObjectMapper objectMapper = new ObjectMapper();

    public ServiceImpl(RepositoryProvider repositoryProvider) {
        this.repositoryProvider = repositoryProvider;
    }

    protected Long findMandatoryBuild(Long jobId, String buildName) {
        return provideMandatoryIdFor("Build", buildName, () -> jobs().findBuildIdByJobAndName(jobId, buildName));
    }

    protected Long findMandatoryProject(String projectName) {
        return provideMandatoryIdFor("Project", projectName, () -> projects().findProjectIdByName(projectName));
    }

    protected Long findMandatoryJob(Long projectId, String jobName) {
        return provideMandatoryIdFor("Job", jobName, () -> jobs().findJobIdByProjectAndName(projectId, jobName));
    }

    private Long provideMandatoryIdFor(String entity, String entityName, Supplier<Optional<Long>> supplier) {
        Optional<Long> id = supplier.get();
        if (!id.isPresent()) {
            throw new RuntimeException(entity + " does not exist: " + entityName);
        }
        return id.get();
    }

    public ProjectsRepository projects() {
        return repositoryProvider.projects();
    }

    public JobsRepository jobs() {
        return repositoryProvider.jobs();
    }

    public TestsRepository tests() {
        return repositoryProvider.tests();
    }

}
