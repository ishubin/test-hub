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
package net.mindengine.testhub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import spark.ResponseTransformer;

import java.text.SimpleDateFormat;


public class JsonTransformer implements ResponseTransformer {
    private final ObjectMapper objectMapper;

    private JsonTransformer() {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.getSerializationConfig().with(new ISO8601DateFormat());
    }

    @Override
    public String render(Object model) throws Exception {
        return objectMapper.writeValueAsString(model);
    }

    private final static JsonTransformer _instance = new JsonTransformer();

    public static JsonTransformer toJson() {
        return _instance;
    }
}
