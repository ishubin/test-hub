package net.mindengine.testhub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mindengine.testhub.model.projects.Project;
import net.mindengine.testhub.repository.projects.ProjectRepository;

import static net.mindengine.testhub.controllers.JsonTransformer.toJson;
import static spark.Spark.*;

public class ProjectApiController {
    private final ProjectRepository projectRepository;
    private ObjectMapper mapper = new ObjectMapper();

    public ProjectApiController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        init();
    }

    private void init() {
        post("/api/projects", (req, res) -> {
            return projectRepository.createProject(mapper.readValue(req.body(), Project.class));
        }, toJson());
    }
}
