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
package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.tests.*;
import net.mindengine.testhub.services.TestService;

import java.util.Optional;

public class TestsApiController extends Controller {
    private final TestService testService;

    public TestsApiController(TestService testService) {
        this.testService = testService;
        init();
    }

    private void init() {
        postJson("/api/projects/:project/tests", (req, res) ->
            testService.createTest(req.params("project"), fromJson(req, TestRequest.class))
        );

        getJson("/api/projects/:project/jobs/:job/builds/:build/tests", (req, res) -> testService.findTests(
            req.params("project"),
            req.params("job"),
            req.params("build"),
            Optional.ofNullable(req.queryParams("status"))
        ));

        getJson("/api/projects/:project/jobs/:job/builds/:build/tests/:testId", (req, res) -> testService.findTest(
            req.params("project"),
            req.params("job"),
            req.params("build"),
            Long.parseLong(req.params("testId"))
        ));
    }

}
