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
package net.mindengine.testhub.model.builds;

public class Build {
    private Long buildId;
    private Long jobId;
    private String name;
    private Long cntTestsPassed;
    private Long cntTestsFailed;
    private Long cntTestsSkipped;

    public Build() {
    }

    public Build(Long buildId, Long jobId, String name, Long cntTestsPassed, Long cntTestsFailed, Long cntTestsSkipped) {
        this.buildId = buildId;
        this.jobId = jobId;
        this.name = name;
        this.cntTestsPassed = cntTestsPassed;
        this.cntTestsFailed = cntTestsFailed;
        this.cntTestsSkipped = cntTestsSkipped;
    }


    public Long getBuildId() {
        return buildId;
    }

    public void setBuildId(Long buildId) {
        this.buildId = buildId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCntTestsPassed() {
        return cntTestsPassed;
    }

    public void setCntTestsPassed(Long cntTestsPassed) {
        this.cntTestsPassed = cntTestsPassed;
    }

    public Long getCntTestsFailed() {
        return cntTestsFailed;
    }

    public void setCntTestsFailed(Long cntTestsFailed) {
        this.cntTestsFailed = cntTestsFailed;
    }

    public Long getCntTestsSkipped() {
        return cntTestsSkipped;
    }

    public void setCntTestsSkipped(Long cntTestsSkipped) {
        this.cntTestsSkipped = cntTestsSkipped;
    }
}
