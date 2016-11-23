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
package net.mindengine.testhub.jobs;

import net.mindengine.testhub.services.JobsService;

import java.util.Date;

public class DataCleanupJob implements Runnable {
    private final JobsService jobsService;
    private final long cleanupPeriod = 10 * 24 * 3600 * 1000;

    public DataCleanupJob(JobsService jobsService) {
        this.jobsService = jobsService;
    }

    @Override
    public void run() {
        try {
            Date cleanupDate = new Date(new Date().getTime() - cleanupPeriod);
            jobsService.removeBuildsOlderThan(cleanupDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
