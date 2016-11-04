package net.mindengine.testhub.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mindengine.testhub.repository.RepositoryProvider;
import net.mindengine.testhub.repository.files.FilesRepository;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;
import spark.ModelAndView;
import spark.Request;
import spark.Route;
import spark.TemplateEngine;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static net.mindengine.testhub.controllers.JsonTransformer.toJson;
import static spark.Spark.get;
import static spark.Spark.post;

public class Controller {
    private final RepositoryProvider repositoryProvider;

    private final TemplateEngine templateEngine = new HandlebarsTemplateEngine();
    protected ObjectMapper objectMapper = new ObjectMapper();

    public Controller(RepositoryProvider repositoryProvider) {
        this.repositoryProvider = repositoryProvider;
    }

    public <T> T fromJson(Request req, Class<T> clazz) throws IOException {
        return objectMapper.readValue(req.body(), clazz);
    }

    public static void postJson(String path, Route route) {
        post(path, route, toJson());
    }

    public static void postJson(String path, String acceptType, Route route) {
        post(path, acceptType, route, toJson());
    }

    public static void getJson(String path, Route route) {
        get(path, route, toJson());
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


    public void getHsTpl(String path, String templateName, BiConsumer<Request, Map> consumer) {
        get(path, (req, res) -> {
            Map model = new HashMap();
            consumer.accept(req, model);
            return new ModelAndView(model, templateName);
        }, templateEngine);

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

    public FilesRepository files() {
        return repositoryProvider.files();
    }
}
