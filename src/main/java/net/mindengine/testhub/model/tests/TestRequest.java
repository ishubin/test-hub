package net.mindengine.testhub.model.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;


public class TestRequest extends TestBaseData {
    private TestReportRequest testReport;

    public TestReportRequest getTestReport() {
        return testReport;
    }

    public void setTestReport(TestReportRequest testReport) {
        this.testReport = testReport;
    }

    public Test asTest(ObjectMapper objectMapper) throws JsonProcessingException {
        Test test = new Test();
        test.setName(getTestName());
        test.setError(getError());
        test.setReason(getReason());
        test.setStatus(getStatus());
        test.setReportedBy(getReportedBy());
        test.setStartedDate(getStartedDate());
        test.setEndedDate(getEndedDate());

        if (getTestReport() != null) {
            test.setReportType(getTestReport().getReportType());
            String convertedValue;
            if (getTestReport().getReport() instanceof TextNode) {
                convertedValue = getTestReport().getReport().asText();
            } else {
                convertedValue = objectMapper.writeValueAsString(getTestReport().getReport());
            }
            test.setReport(convertedValue);
        }
        return test;
    }
}
