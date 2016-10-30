package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.projects.Project;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;

public class ProjectsApiController extends ApiController {

    public ProjectsApiController(ProjectsRepository projectsRepository, JobsRepository jobsRepository) {
        super(projectsRepository, jobsRepository);
        init();
    }

    private void init() {
        postJson("/api/projects", (req, res) -> projectsRepository.createProject(fromJson(req, Project.class)));
    }


}
