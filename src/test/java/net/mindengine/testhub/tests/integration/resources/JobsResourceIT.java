package net.mindengine.testhub.tests.integration.resources;

import net.mindengine.testhub.model.builds.BuildResponse;
import net.mindengine.testhub.model.jobs.JobResponse;
import net.mindengine.testhub.model.tests.TestStatistics;
import net.mindengine.testhub.model.tests.TestStatus;
import net.mindengine.testhub.services.JobsService;
import net.mindengine.testhub.tests.components.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class JobsResourceIT extends ResourceTestBase {

    JobsService jobsService = registerMock(JobsService.class);

    @Test
    public void should_retrieve_list_of_jobs_in_project() throws IOException {
        when(jobsService.findJobs("demo")).thenReturn(asList(
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
    }
}
