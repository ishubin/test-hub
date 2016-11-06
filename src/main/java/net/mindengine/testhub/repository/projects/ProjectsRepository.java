package net.mindengine.testhub.repository.projects;

import net.mindengine.testhub.model.projects.Project;

import java.util.Optional;

public interface ProjectsRepository {
    Long createProject(Project project);

    Optional<Long> findProjectIdByName(String projectName);
}
