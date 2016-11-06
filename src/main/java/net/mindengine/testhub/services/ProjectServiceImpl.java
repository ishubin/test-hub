package net.mindengine.testhub.services;

import net.mindengine.testhub.model.projects.Project;
import net.mindengine.testhub.repository.RepositoryProvider;

public class ProjectServiceImpl extends ServiceImpl implements ProjectService {
    public ProjectServiceImpl(RepositoryProvider repositoryProvider) {
        super(repositoryProvider);

    }

    @Override
    public void createProject(Project project) {
        projects().createProject(project);
    }
}
