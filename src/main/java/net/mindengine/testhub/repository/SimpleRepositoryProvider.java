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
package net.mindengine.testhub.repository;

import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

public class SimpleRepositoryProvider implements RepositoryProvider {
    private final ProjectsRepository projectRepository;
    private final JobsRepository jobsRepository;
    private final TestsRepository testsRepository;

    public SimpleRepositoryProvider(ProjectsRepository projectRepository, JobsRepository jobsRepository, TestsRepository testsRepository) {
        this.projectRepository = projectRepository;
        this.jobsRepository = jobsRepository;
        this.testsRepository = testsRepository;
    }

    @Override
    public ProjectsRepository projects() {
        return projectRepository;
    }

    @Override
    public JobsRepository jobs() {
        return jobsRepository;
    }

    @Override
    public TestsRepository tests() {
        return testsRepository;
    }
}
