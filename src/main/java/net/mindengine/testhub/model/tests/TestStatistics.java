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
package net.mindengine.testhub.model.tests;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TestStatistics {

    private long passed;
    private long failed;
    private long skipped;

    public TestStatistics() {
    }

    public TestStatistics(Long passed, Long failed, Long skipped) {
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
    }

    public void setPassed(long passed) {
        this.passed = passed;
    }

    public Long getPassed() {
        return passed;
    }

    public void setFailed(long failed) {
        this.failed = failed;
    }

    public Long getFailed() {
        return failed;
    }

    public void setSkipped(long skipped) {
        this.skipped = skipped;
    }

    public Long getSkipped() {
        return skipped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TestStatistics that = (TestStatistics) o;

        return new EqualsBuilder()
            .append(passed, that.passed)
            .append(failed, that.failed)
            .append(skipped, that.skipped)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(passed)
            .append(failed)
            .append(skipped)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("passed", passed)
            .append("failed", failed)
            .append("skipped", skipped)
            .toString();
    }
}
