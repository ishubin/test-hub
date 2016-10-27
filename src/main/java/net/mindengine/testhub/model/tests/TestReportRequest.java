package net.mindengine.testhub.model.tests;

import com.fasterxml.jackson.databind.JsonNode;

public class TestReportRequest {
    private String reportType;
    private JsonNode report;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public JsonNode getReport() {
        return report;
    }

    public void setReport(JsonNode report) {
        this.report = report;
    }
}
