package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.projects.Project;
import net.mindengine.testhub.services.ProjectService;

public class ProjectsApiController extends Controller {

    private final ProjectService projectService;

    public ProjectsApiController(ProjectService projectService) {
        this.projectService = projectService;
        init();
    }

    private void init() {
        postJson("/api/projects", (req, res) -> {
            Project project = fromJson(req, Project.class);
            projectService.createProject(project);
            return project;
        });
    }


}
