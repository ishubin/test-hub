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

import net.mindengine.testhub.model.tests.TestStatistics;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BuildResponse {
    private String name;
    private TestStatistics testStatistics;

    public BuildResponse(String name, TestStatistics testStatistics) {
        this.name = name;
        this.testStatistics = testStatistics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestStatistics getTestStatistics() {
        return testStatistics;
    }

    public void setTestStatistics(TestStatistics testStatistics) {
        this.testStatistics = testStatistics;
    }

    public static BuildResponse from(Build build) {
        if (build == null) {
            return null;
        }

        return new BuildResponse(
            build.getName(),
            new TestStatistics(build.getCntTestsPassed(), build.getCntTestsFailed(), build.getCntTestsSkipped())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BuildResponse that = (BuildResponse) o;

        return new EqualsBuilder()
            .append(name, that.name)
            .append(testStatistics, that.testStatistics)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(name)
            .append(testStatistics)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .append("testStatistics", testStatistics)
            .toString();
    }
}
