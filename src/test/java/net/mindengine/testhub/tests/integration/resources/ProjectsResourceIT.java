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

import net.mindengine.testhub.model.projects.Project;
import net.mindengine.testhub.services.ProjectService;
import net.mindengine.testhub.tests.components.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

public class ProjectsResourceIT extends ResourceTestBase {

    private ProjectService projectService = registerMock(ProjectService.class);


    @Test
    public void should_create_project_with_post_request() throws IOException {
        Response response = postJson("/api/projects", "{\"name\": \"demo\"}");
        assertThat(response.getCode(), is(200));
        assertThat(response.getBody(), is("{\"name\":\"demo\"}"));
        verify(projectService).createProject(new Project("demo"));
    }
    
}
