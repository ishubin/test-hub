package net.mindengine.testhub.model.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.Date;

public class TestRequest {
    private String job;
    private String build;
    private String testName;
    private String status;
    private String reason;
    private String error;
    private String reportedBy;
    private Date startedDate;
    private Date endedDate;
    private TestReportRequest testReport;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public TestReportRequest getTestReport() {
        return testReport;
    }

    public void setTestReport(TestReportRequest testReport) {
        this.testReport = testReport;
    }

    public Test asTest(ObjectMapper objectMapper) throws JsonProcessingException {
        Test test = new Test();
        test.setName(testName);
        test.setError(error);
        test.setReason(reason);
        test.setStatus(status);
        test.setReportedBy(reportedBy);
        test.setStartedDate(startedDate);
        test.setEndedDate(endedDate);

        if (testReport != null) {
            test.setReportType(testReport.getReportType());
            String convertedValue = null;
            if (testReport.getReport() instanceof TextNode) {
                convertedValue = testReport.getReport().asText();
            } else {
                convertedValue = objectMapper.writeValueAsString(testReport.getReport());
            }
            test.setReport(convertedValue);
        }
        return test;
    }
}
