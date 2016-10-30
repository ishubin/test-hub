package net.mindengine.testhub.model.tests;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestResponse extends TestBaseData {

    private TestReport testReport;
    private TestHistory[] testHistory;

    public static TestResponse from(String job, String build, Test test, ObjectMapper objectMapper) {
        TestResponse r = new TestResponse();
        r.setTestName(test.getName());
        r.setError(test.getError());
        r.setStatus(test.getStatus());
        r.setBuild(build);
        r.setJob(job);
        r.setReason(test.getReason());
        r.setStartedDate(test.getStartedDate());
        r.setEndedDate(test.getEndedDate());
        r.setReportedBy(test.getReportedBy());
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
