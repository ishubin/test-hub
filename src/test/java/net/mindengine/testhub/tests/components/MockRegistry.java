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
package net.mindengine.testhub.tests.components;

import javafx.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum MockRegistry {
    INSTANCE;

    private Map<Pair<String, String>, Object> mocks = Collections.synchronizedMap(new HashMap<>());

    public static <T> void registerMock(String sessionId, T mock, String mockName) {
        MockRegistry.INSTANCE.mocks.put(new Pair<>(sessionId, mockName), mock);
    }

    public static Object pickMock(String sessionId, String mockName) {
        return MockRegistry.INSTANCE.mocks.get(new Pair<>(sessionId, mockName));
    }
}
