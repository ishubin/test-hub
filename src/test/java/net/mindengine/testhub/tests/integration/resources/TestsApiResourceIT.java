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

import net.mindengine.testhub.model.tests.TestRequest;
import net.mindengine.testhub.model.tests.TestResponse;
import net.mindengine.testhub.services.TestService;
import net.mindengine.testhub.tests.components.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestsApiResourceIT extends ResourceIntegrationTestBase {

    TestService testService = registerMock(TestService.class);

    @Test
    public void should_post_test() throws IOException {
        when(testService.createTest(anyString(), anyObject())).thenReturn(objectFromResources("/expected-responses/test-response-1.json", TestResponse.class));

        Response response = postJson("/api/projects/demo/tests", jsonFromResources("/requests/test-request-1.json"));
        assertThat(response.getCode(), is(200));
        assertThat(response.getBody(), is(jsonFromResources("/expected-responses/test-response-1.json")));

        verify(testService).createTest("demo", objectFromResources("/requests/test-request-1.json", TestRequest.class));
        verifyNoMoreInteractionsOnAllMocks();
    }


}
