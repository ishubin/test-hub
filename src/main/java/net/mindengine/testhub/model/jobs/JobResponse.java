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
package net.mindengine.testhub.model.jobs;

import net.mindengine.testhub.model.builds.BuildResponse;
import net.mindengine.testhub.model.tests.TestStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class JobResponse {
    private BuildResponse latestBuild;
    private String name;
    private TestStatus status;

    public JobResponse(String name, BuildResponse latestBuild, TestStatus status) {
        this.name = name;
        this.latestBuild = latestBuild;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BuildResponse getLatestBuild() {
        return latestBuild;
    }

    public void setLatestBuild(BuildResponse latestBuild) {
        this.latestBuild = latestBuild;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        JobResponse that = (JobResponse) o;

        return new EqualsBuilder()
            .append(latestBuild, that.latestBuild)
            .append(name, that.name)
            .append(status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(latestBuild)
            .append(name)
            .append(status)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("latestBuild", latestBuild)
            .append("name", name)
            .append("status", status)
            .toString();
    }
}
