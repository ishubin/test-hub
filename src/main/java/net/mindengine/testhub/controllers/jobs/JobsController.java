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
package net.mindengine.testhub.controllers.jobs;

import net.mindengine.testhub.controllers.api.Controller;
import net.mindengine.testhub.services.JobsService;

public class JobsController extends Controller {
    private final JobsService jobsService;

    public JobsController(JobsService jobsService) {
        this.jobsService = jobsService;
        init();
    }

    private void init() {
        getHsTpl("/projects/:project/jobs", "jobs", (req, model) -> {
            model.put("jobs", jobsService.findJobs(req.params("project")));
        });
    }


}
