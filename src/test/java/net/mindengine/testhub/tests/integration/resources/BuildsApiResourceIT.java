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
import net.mindengine.testhub.model.tests.TestStatistics;
import net.mindengine.testhub.services.JobsService;
import net.mindengine.testhub.tests.components.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuildsApiResourceIT extends ResourceIntegrationTestBase {

    JobsService jobsService = registerMock(JobsService.class);

    @Test
    public void should_get_list_of_builds_for_job() throws IOException {
        when(jobsService.findBuilds(anyString(), anyString())).thenReturn(asList(
            new BuildResponse("123", new TestStatistics(4L, 0L, 3L)),
            new BuildResponse("124", new TestStatistics(1L, 2L, 3L))
        ));

        Response response = getJson("/api/projects/demo/jobs/UI_tests/builds");
        assertThat(response.getCode(), is(200));
        assertThat(response.getBody(), is(jsonFromResources("/expected-responses/builds-success-response-1.json")));

        verify(jobsService).findBuilds("demo", "UI_tests");
        verifyNoMoreInteractionsOnAllMocks();
    }


    @Test
    public void should_get_a_single_build_for_job() throws IOException {
        when(jobsService.findBuild(anyString(), anyString(), anyString())).thenReturn(
            new BuildResponse("123", new TestStatistics(4L, 0L, 3L))
        );

        Response response = getJson("/api/projects/demo/jobs/UI_tests/builds/23");
        assertThat(response.getCode(), is(200));
        assertThat(response.getBody(), is(jsonFromResources("/expected-responses/build-single-success-response-1.json")));

        verify(jobsService).findBuild("demo", "UI_tests", "23");
        verifyNoMoreInteractionsOnAllMocks();
    }
}
