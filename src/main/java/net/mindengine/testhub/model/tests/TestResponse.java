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

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mindengine.testhub.model.Attachment;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class TestResponse extends TestBaseData {

    private TestReport testReport;
    private TestHistory[] testHistory;


    public static TestResponse from(String job, String build, Test test, List<Attachment> attachments, ObjectMapper objectMapper) {
        TestResponse r = new TestResponse();
        r.setTestId(test.getTestId());
        r.setTestName(test.getName());
        r.setError(test.getError());
        r.setStatus(test.getStatus());
        r.setBuild(build);
        r.setJob(job);
        r.setReason(test.getReason());
        r.setStartedDate(test.getStartedDate());
        r.setEndedDate(test.getEndedDate());
        r.setReportedBy(test.getReportedBy());
        r.setAttachments(attachments);
        r.setTestReport(new TestReport(test.getReportType(), parseTestReport(test.getReport(), objectMapper)));
        r.setTestHistory(convertSafelyTestHistoryFromJson(test, objectMapper));
        return r;
    }

    private static Object parseTestReport(String report, ObjectMapper objectMapper) {
        //TODO find a nicer way
        try {
            report = report.trim();
            if (report.startsWith("{") && report.endsWith("}")
                || report.startsWith("[") && report.endsWith("]")) {
                return objectMapper.readTree(report);
            }
        } catch (Exception ex) {
        }
        return report;
    }

    private static TestHistory[] convertSafelyTestHistoryFromJson(Test test, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(test.getAggregatedStatusHistory(), TestHistory[].class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public TestReport getTestReport() {
        return testReport;
    }

    public TestResponse setTestReport(TestReport testReport) {
        this.testReport = testReport;
        return this;
    }

    public TestHistory[] getTestHistory() {
        return testHistory;
    }

    public TestHistory[] setTestHistory(TestHistory[] testHistory) {
        this.testHistory = testHistory;
        return testHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TestResponse that = (TestResponse) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(testReport, that.testReport)
            .append(testHistory, that.testHistory)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(testReport)
            .append(testHistory)
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
            .append("testReport", testReport)
            .append("testHistory", testHistory)
            .toString();
    }



}
