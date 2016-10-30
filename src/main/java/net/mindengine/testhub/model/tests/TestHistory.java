package net.mindengine.testhub.model.tests;

public class TestHistory {
    private Long testRunId;
    private String buildName;
    private String status;
    private String reason;

    public TestHistory() {
    }

    public TestHistory(Long testRunId, String buildName, String status, String reason) {
        this.testRunId = testRunId;
        this.buildName = buildName;
        this.status = status;
        this.reason = reason;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
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

    public Long getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(Long testRunId) {
        this.testRunId = testRunId;
    }
}
