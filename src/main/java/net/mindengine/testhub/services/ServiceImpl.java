package net.mindengine.testhub.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mindengine.testhub.repository.RepositoryProvider;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class ServiceImpl {
    private final RepositoryProvider repositoryProvider;
    ObjectMapper objectMapper = new ObjectMapper();

    public ServiceImpl(RepositoryProvider repositoryProvider) {
        this.repositoryProvider = repositoryProvider;
    }

    protected Long findMandatoryBuild(Long jobId, String buildName) {
        return provideMandatoryIdFor("Build", buildName, () -> jobs().findBuildIdByJobAndName(jobId, buildName));
    }

    protected Long findMandatoryProject(String projectName) {
        return provideMandatoryIdFor("Project", projectName, () -> projects().findProjectIdByName(projectName));
    }

    protected Long findMandatoryJob(Long projectId, String jobName) {
        return provideMandatoryIdFor("Job", jobName, () -> jobs().findJobIdByProjectAndName(projectId, jobName));
    }

    private Long provideMandatoryIdFor(String entity, String entityName, Supplier<Optional<Long>> supplier) {
        Optional<Long> id = supplier.get();
        if (!id.isPresent()) {
            throw new RuntimeException(entity + " does not exist: " + entityName);
        }
        return id.get();
    }

    public ProjectsRepository projects() {
        return repositoryProvider.projects();
    }

    public JobsRepository jobs() {
        return repositoryProvider.jobs();
    }

    public TestsRepository tests() {
        return repositoryProvider.tests();
    }

}
