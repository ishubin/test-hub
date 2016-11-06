package net.mindengine.testhub.model.builds;

import net.mindengine.testhub.model.tests.TestStatistics;

public class BuildResponse {
    private String name;
    private TestStatistics testStatistics;

    public BuildResponse(String name, TestStatistics testStatistics) {
        this.name = name;
        this.testStatistics = testStatistics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestStatistics getTestStatistics() {
        return testStatistics;
    }

    public void setTestStatistics(TestStatistics testStatistics) {
        this.testStatistics = testStatistics;
    }

    public static BuildResponse from(Build build) {
        if (build == null) {
            return null;
        }

        return new BuildResponse(
            build.getName(),
            new TestStatistics(build.getCntTestsPassed(), build.getCntTestsFailed(), build.getCntTestsSkipped())
        );
    }
}
