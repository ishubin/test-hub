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
