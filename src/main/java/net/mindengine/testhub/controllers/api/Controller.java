/*******************************************************************************
 * Copyright 2016 Ivan Shubin https://github.com/ishubin/test-hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
