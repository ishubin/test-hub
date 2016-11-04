package net.mindengine.testhub.model.jobs;

public class Job {
    private Long jobId;
    private String name;
    private Long projectId;

    public Job(Long jobId, String name) {
        this.jobId = jobId;
        this.name = name;
    }

    public Job(Long jobId, Long projectId, String name) {
        this.jobId = jobId;
        this.projectId = projectId;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

}
