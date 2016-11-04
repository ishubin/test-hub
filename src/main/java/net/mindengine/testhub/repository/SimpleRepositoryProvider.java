package net.mindengine.testhub.repository;

import net.mindengine.testhub.repository.files.FilesRepository;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

public class SimpleRepositoryProvider implements RepositoryProvider {
    private final ProjectsRepository projectRepository;
    private final JobsRepository jobsRepository;
    private final TestsRepository testsRepository;
    private final FilesRepository filesRepository;

    public SimpleRepositoryProvider(ProjectsRepository projectRepository, JobsRepository jobsRepository, TestsRepository testsRepository, FilesRepository filesRepository) {
        this.projectRepository = projectRepository;
        this.jobsRepository = jobsRepository;
        this.testsRepository = testsRepository;
        this.filesRepository = filesRepository;
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

    @Override
    public FilesRepository files() {
        return filesRepository;
    }
}
