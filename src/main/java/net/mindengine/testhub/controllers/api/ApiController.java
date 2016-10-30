package net.mindengine.testhub.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import spark.Request;
import spark.Route;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

import static net.mindengine.testhub.controllers.JsonTransformer.toJson;
import static spark.Spark.get;
import static spark.Spark.post;

public class ApiController {
    protected final ProjectsRepository projectsRepository;
    protected final JobsRepository jobsRepository;

    protected ObjectMapper objectMapper = new ObjectMapper();

    public ApiController(ProjectsRepository projectRepository, JobsRepository jobsRepository) {
        this.projectsRepository = projectRepository;
        this.jobsRepository = jobsRepository;
    }

    public <T> T fromJson(Request req, Class<T> clazz) throws IOException {
        return objectMapper.readValue(req.body(), clazz);
    }

    public static void postJson(String path, Route route) {
        post(path, route, toJson());
    }

    public static void getJson(String path, Route route) {
        get(path, route, toJson());
    }

    protected Long findMandatoryBuild(Long jobId, String buildName) {
        return provideMandatoryIdFor("Build", buildName, () -> jobsRepository.findBuildByJobAndName(jobId, buildName));
    }

    protected Long findMandatoryProject(String projectName) {
        return provideMandatoryIdFor("Project", projectName, () -> projectsRepository.findProjectIdByName(projectName));
    }

    protected Long findMandatoryJob(Long projectId, String jobName) {
        return provideMandatoryIdFor("Job", jobName, () -> jobsRepository.findJobIdByProjectAndName(projectId, jobName));
    }

    private Long provideMandatoryIdFor(String entity, String entityName, Supplier<Optional<Long>> supplier) {
        Optional<Long> id = supplier.get();
        if (!id.isPresent()) {
            throw new RuntimeException(entity + " does not exist: " + entityName);
        }
        return id.get();
    }
}
