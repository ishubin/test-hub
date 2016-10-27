package net.mindengine.testhub.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Route;

import java.io.IOException;

import static net.mindengine.testhub.controllers.JsonTransformer.toJson;
import static spark.Spark.post;

public class ApiController {
    protected ObjectMapper objectMapper = new ObjectMapper();

    public <T> T fromJson(Request req, Class<T> clazz) throws IOException {
        return objectMapper.readValue(req.body(), clazz);
    }

    public static void postJson(String path, Route route) {
        post(path, route, toJson());
    }

}
