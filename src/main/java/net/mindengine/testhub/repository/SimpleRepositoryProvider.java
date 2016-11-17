package net.mindengine.testhub.repository;

import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

public class SimpleRepositoryProvider implements RepositoryProvider {
    private final ProjectsRepository projectRepository;
    private final JobsRepository jobsRepository;
    private final TestsRepository testsRepository;

    public SimpleRepositoryProvider(ProjectsRepository projectRepository, JobsRepository jobsRepository, TestsRepository testsRepository) {
        this.projectRepository = projectRepository;
        this.jobsRepository = jobsRepository;
        this.testsRepository = testsRepository;
    }

    @Override
    public ProjectsRepository projects() {
        return projectRepository;
    }

    @Override
    public JobsRepository jobs() {
        return jobsRepository;
    }

    @Override
    public TestsRepository tests() {
        return testsRepository;
    }
}
