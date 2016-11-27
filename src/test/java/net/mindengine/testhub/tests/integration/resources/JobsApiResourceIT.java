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
package net.mindengine.testhub.tests.integration.resources;

import net.mindengine.testhub.model.builds.BuildResponse;
import net.mindengine.testhub.model.jobs.JobResponse;
import net.mindengine.testhub.model.tests.TestStatistics;
import net.mindengine.testhub.model.tests.TestStatus;
import net.mindengine.testhub.services.JobsService;
import net.mindengine.testhub.tests.components.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JobsApiResourceIT extends ResourceIntegrationTestBase {

    JobsService jobsService = registerMock(JobsService.class);

    @Test
    public void should_get_list_of_jobs_in_project() throws IOException {
        when(jobsService.findJobs(anyString())).thenReturn(asList(
            new JobResponse("Some_tests", new BuildResponse(
                "#123", new TestStatistics(1L, 3L, 4L)
            ), TestStatus.failed),
            new JobResponse("Some_tests_2", new BuildResponse(
                "#32", new TestStatistics(40L, 0L, 5L)
            ), TestStatus.passed)
        ));

        Response response = getJson("/api/projects/demo/jobs");
        assertThat(response.getCode(), is(200));
        assertThat(response.getBody(), is(jsonFromResources("/expected-responses/jobs-success-response-1.json")));

        verify(jobsService).findJobs("demo");
        verifyNoMoreInteractionsOnAllMocks();
    }

    @Test
    public void should_get_single_job() throws IOException {
        when(jobsService.findJob(anyString(), anyString())).thenReturn(Optional.of(
            new JobResponse("Some_tests", new BuildResponse(
                "#123", new TestStatistics(1L, 3L, 4L)
            ), TestStatus.failed)
        ));

        Response response = getJson("/api/projects/demo/jobs/Some_tests");
        assertThat(response.getCode(), is(200));
        assertThat(response.getBody(), is(jsonFromResources("/expected-responses/jobs-single-success-response-1.json")));

        verify(jobsService).findJob("demo", "Some_tests");
        verifyNoMoreInteractionsOnAllMocks();
    }
}
