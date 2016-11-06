package net.mindengine.testhub.model.builds;

public class Build {
    private Long buildId;
    private Long jobId;
    private String name;
    private Long cntTestsPassed;
    private Long cntTestsFailed;
    private Long cntTestsSkipped;

    public Build() {
    }

    public Build(Long buildId, Long jobId, String name, Long cntTestsPassed, Long cntTestsFailed, Long cntTestsSkipped) {
        this.buildId = buildId;
        this.jobId = jobId;
        this.name = name;
        this.cntTestsPassed = cntTestsPassed;
        this.cntTestsFailed = cntTestsFailed;
        this.cntTestsSkipped = cntTestsSkipped;
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

    public Long getCntTestsPassed() {
        return cntTestsPassed;
    }

    public void setCntTestsPassed(Long cntTestsPassed) {
        this.cntTestsPassed = cntTestsPassed;
    }

    public Long getCntTestsFailed() {
        return cntTestsFailed;
    }

    public void setCntTestsFailed(Long cntTestsFailed) {
        this.cntTestsFailed = cntTestsFailed;
    }

    public Long getCntTestsSkipped() {
        return cntTestsSkipped;
    }

    public void setCntTestsSkipped(Long cntTestsSkipped) {
        this.cntTestsSkipped = cntTestsSkipped;
    }
}
