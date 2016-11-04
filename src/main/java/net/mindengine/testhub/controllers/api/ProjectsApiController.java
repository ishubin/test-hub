package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.projects.Project;
import net.mindengine.testhub.repository.RepositoryProvider;

public class ProjectsApiController extends Controller {

    public ProjectsApiController(RepositoryProvider repositoryProvider) {
        super(repositoryProvider);
        init();
    }

    private void init() {
        postJson("/api/projects", (req, res) -> projects().createProject(fromJson(req, Project.class)));
    }


}
