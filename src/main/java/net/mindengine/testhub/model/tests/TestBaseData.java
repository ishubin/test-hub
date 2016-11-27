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

import net.mindengine.testhub.model.Attachment;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

public class TestBaseData {
    protected Long testId;
    protected String job;
    protected String build;
    protected String testName;
    protected TestStatus status;
    protected String reason;
    protected String error;
    protected String reportedBy;
    protected Date startedDate;
    protected Date endedDate;
    protected List<Attachment> attachments;


    public TestBaseData() {
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TestBaseData that = (TestBaseData) o;

        return new EqualsBuilder()
            .append(testId, that.testId)
            .append(job, that.job)
            .append(build, that.build)
            .append(testName, that.testName)
            .append(status, that.status)
            .append(reason, that.reason)
            .append(error, that.error)
            .append(reportedBy, that.reportedBy)
            .append(startedDate, that.startedDate)
            .append(endedDate, that.endedDate)
            .append(attachments, that.attachments)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(testId)
            .append(job)
            .append(build)
            .append(testName)
            .append(status)
            .append(reason)
            .append(error)
            .append(reportedBy)
            .append(startedDate)
            .append(endedDate)
            .append(attachments)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("testId", testId)
            .append("job", job)
            .append("build", build)
            .append("testName", testName)
            .append("status", status)
            .append("reason", reason)
            .append("error", error)
            .append("reportedBy", reportedBy)
            .append("startedDate", startedDate)
            .append("endedDate", endedDate)
            .append("attachments", attachments)
            .toString();
    }

}
