package net.mindengine.testhub.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mindengine.testhub.templates.HandlebarsTemplateEngine;
import spark.ModelAndView;
import spark.Request;
import spark.Route;
import spark.TemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static net.mindengine.testhub.controllers.JsonTransformer.toJson;
import static spark.Spark.get;
import static spark.Spark.post;

public class Controller {
    private final TemplateEngine templateEngine = createHandlebarsEngine();

    private HandlebarsTemplateEngine createHandlebarsEngine() {
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine("/templates");
        return engine;
    }

    protected ObjectMapper objectMapper = new ObjectMapper();

    public <T> T fromJson(Request req, Class<T> clazz) throws IOException {
        return objectMapper.readValue(req.body(), clazz);
    }

    public static void postJson(String path, Route route) {
        post(path, (req, res) -> {
            res.header("Content-Type", "application/json");
            return route.handle(req, res);
        }, toJson());
    }

    public static void postJson(String path, String acceptType, Route route) {
        post(path, acceptType, (req, res) -> {
            res.header("Content-Type", "application/json");
            return route.handle(req, res);
        }, toJson());
    }

    public static void getJson(String path, Route route) {
        get(path, (req, res) -> {
            res.header("Content-Type", "application/json");
            return route.handle(req, res);
        }, toJson());
    }


    public void getHsTpl(String path, String templateName, BiConsumer<Request, Map<String, Object>> consumer) {
        get(path, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            consumer.accept(req, model);
            return new ModelAndView(model, templateName);
        }, templateEngine);

    }
}
