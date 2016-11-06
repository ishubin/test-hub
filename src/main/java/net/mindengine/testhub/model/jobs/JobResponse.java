package net.mindengine.testhub.model.jobs;

import net.mindengine.testhub.model.builds.BuildResponse;
import net.mindengine.testhub.model.tests.TestStatus;

public class JobResponse {
    private BuildResponse latestBuild;
    private String name;
    private TestStatus status;

    public JobResponse(String name, BuildResponse latestBuild, TestStatus status) {
        this.name = name;
        this.latestBuild = latestBuild;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BuildResponse getLatestBuild() {
        return latestBuild;
    }

    public void setLatestBuild(BuildResponse latestBuild) {
        this.latestBuild = latestBuild;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }
}
