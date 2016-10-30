package net.mindengine.testhub.model.tests;

public class TestReport {
    private String reportType;
    private String report;

    public TestReport(String reportType, String report) {
        this.reportType = reportType;
        this.report = report;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
