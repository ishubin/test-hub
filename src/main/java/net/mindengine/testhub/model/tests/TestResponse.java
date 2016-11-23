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
        r.setTestReport(new TestReport(test.getReportType(), test.getReport()));
        r.setTestHistory(convertSafelyTestHistoryFromJson(test, objectMapper));
        return r;
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

    public void setTestReport(TestReport testReport) {
        this.testReport = testReport;
    }

    public TestHistory[] getTestHistory() {
        return testHistory;
    }

    public void setTestHistory(TestHistory[] testHistory) {
        this.testHistory = testHistory;
    }
}
