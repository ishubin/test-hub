package net.mindengine.testhub.model.builds;

public class BuildResponse {
    private String name;

    public BuildResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
