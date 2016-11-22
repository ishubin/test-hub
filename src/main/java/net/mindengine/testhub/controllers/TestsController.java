/*******************************************************************************
 * Copyright 2016 Ivan Shubin https://github.com/ishubin/dash-server
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
package net.mindengine.testhub.controllers;

import net.mindengine.testhub.controllers.api.Controller;

public class TestsController extends Controller {

    public TestsController() {
        init();
    }

    private void init() {
        getHsTpl("/projects/:project/jobs/:job/builds/:build/tests", "tests", (req, model) -> {
            model.put("projectName", req.params("project"));
            model.put("jobName", req.params("job"));
            model.put("buildName", req.params("build"));
            model.put("statusFilter", req.queryParams("status"));
        });

        getHsTpl("/projects/:project/jobs/:job/builds/:build/tests/:testId", "single-test", (req, model) -> {
            model.put("projectName", req.params("project"));
            model.put("jobName", req.params("job"));
            model.put("buildName", req.params("build"));
            model.put("testId", req.params("testId"));
        });
    }
}
