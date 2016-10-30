package net.mindengine.testhub.model.jobs;

public class Job {
    private Long jobId;
    private String name;

    public Job(Long jobId, String name) {
        this.jobId = jobId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
