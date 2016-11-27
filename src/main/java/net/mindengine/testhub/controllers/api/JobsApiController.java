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

import net.mindengine.testhub.model.jobs.JobResponse;
import net.mindengine.testhub.services.JobsService;

import java.util.Optional;


public class JobsApiController extends Controller {
    private final JobsService jobsService;

    public JobsApiController(JobsService jobsService) {
        this.jobsService = jobsService;
        init();
    }

    private void init() {
        getJson("/api/projects/:project/jobs", (req, res) -> jobsService.findJobs(req.params("project")));

        getJson("/api/projects/:project/jobs/:jobName", (req, res) -> {
            Optional<JobResponse> jobResponse = jobsService.findJob(req.params("project"), req.params("jobName"));
            if (!jobResponse.isPresent()) {
                throw new RuntimeException("Job does not exist");
            } else {
                return jobResponse.get();
            }
        });


        getJson("/api/projects/:project/jobs/:jobName/builds", (req, res) ->
            jobsService.findBuilds(req.params("project"), req.params("jobName"))
        );

        getJson("/api/projects/:project/jobs/:jobName/builds/:buildName", (req, res) ->
            jobsService.findBuild(req.params("project"), req.params("jobName"), req.params("buildName"))
        );
    }

}
