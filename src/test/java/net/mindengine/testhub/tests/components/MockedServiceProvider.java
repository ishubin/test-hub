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
package net.mindengine.testhub.tests.components;

import net.mindengine.testhub.services.JobsService;
import net.mindengine.testhub.services.ProjectService;
import net.mindengine.testhub.services.ServiceProvider;
import net.mindengine.testhub.services.TestService;

public class MockedServiceProvider implements ServiceProvider {

    private final ProjectService projectService;
    private final JobsService jobsService;
    private final TestService testService;

    public MockedServiceProvider() {
        projectService = new MockSessionBasedMockedService<>(ProjectService.class).getService();
        jobsService = new MockSessionBasedMockedService<>(JobsService.class).getService();
        testService = new MockSessionBasedMockedService<>(TestService.class).getService();
    }

    @Override
    public ProjectService findProjectService() {
        return projectService;
    }

    @Override
    public JobsService findJobsService() {
        return jobsService;
    }

    @Override
    public TestService findTestService() {
        return testService;
    }
}
