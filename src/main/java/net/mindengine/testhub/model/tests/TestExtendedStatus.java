package net.mindengine.testhub.model.tests;

public class TestExtendedStatus {
    private Long testReportId;
    private String status;
    private String reason;

    public TestExtendedStatus(Long testReportId, String status, String reason) {
        this.testReportId = testReportId;
        this.status = status;
        this.reason = reason;
    }

    public Long getTestReportId() {
        return testReportId;
    }

    public void setTestReportId(Long testReportId) {
        this.testReportId = testReportId;
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
}
