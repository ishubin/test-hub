package net.mindengine.testhub.controllers;

import net.mindengine.testhub.controllers.api.ApiController;
import net.mindengine.testhub.model.projects.Project;
import net.mindengine.testhub.repository.projects.ProjectsRepository;

public class ProjectsApiController extends ApiController {
    private final ProjectsRepository projectsRepository;

    public ProjectsApiController(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
        init();
    }

    private void init() {
        postJson("/api/projects", (req, res) -> projectsRepository.createProject(fromJson(req, Project.class)));
    }


}
