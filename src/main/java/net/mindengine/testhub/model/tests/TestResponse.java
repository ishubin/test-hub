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
