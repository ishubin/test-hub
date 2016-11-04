package net.mindengine.testhub.model.tests;

public class TestStatistics {

    private long passed;
    private long failed;
    private long skipped;

    public void setPassed(long passed) {
        this.passed = passed;
    }

    public Long getPassed() {
        return passed;
    }

    public void setFailed(long failed) {
        this.failed = failed;
    }

    public Long getFailed() {
        return failed;
    }

    public void setSkipped(long skipped) {
        this.skipped = skipped;
    }

    public Long getSkipped() {
        return skipped;
    }
}
