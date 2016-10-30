package net.mindengine.testhub.model.builds;

public class Build {
    private Long buildId;
    private Long jobId;
    private String name;

    public Build(Long buildId, Long jobId, String name) {
        this.buildId = buildId;
        this.jobId = jobId;
        this.name = name;
    }

    public Long getBuildId() {
        return buildId;
    }

    public void setBuildId(Long buildId) {
        this.buildId = buildId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
