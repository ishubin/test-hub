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

import net.mindengine.testhub.Main;
import net.mindengine.testhub.repository.files.LocalFileStorage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static net.mindengine.testhub.Main.FILES_RESOURCE_NAME;
import static spark.Spark.awaitInitialization;
import static spark.Spark.before;
import static spark.Spark.get;

public class MockedWebApp {
    private static MockedWebApp _instance = null;
    public static final String MOCK_KEY_COOKIE_NAME = "__MockUniqueKey__";
    private final String externalLocation = createExternalLocation("target/storage");
    private final String fileStoragePath = externalLocation + File.separator + FILES_RESOURCE_NAME;
    private final Main main;

    private MockedWebApp() {
        main = new Main(new MockedServiceProvider(), FILES_RESOURCE_NAME, new LocalFileStorage(fileStoragePath));
        start();
    }

    private void start() {
        main.startServer(externalLocation);
        initMockRequestFilter();
        initTestStartPage();
        awaitInitialization();
    }

    private void initTestStartPage() {
        get("_test-start_", (req, res) -> "This is a starting point for a test, in order to set the cookie");
    }

    private void initMockRequestFilter() {
        before((request, response) -> {
            CurrentMockSession.clear();
            String mockUniqueKey = request.cookie(MOCK_KEY_COOKIE_NAME);
            if (mockUniqueKey != null) {
                CurrentMockSession.register(mockUniqueKey);
            }
        });
    }

    public synchronized static void create() {
        if (_instance == null) {
            _instance = new MockedWebApp();
        }
    }

    private String createExternalLocation(String path) {
        try {
            FileUtils.forceMkdir(new File(path));
            return path;
        } catch (IOException e) {
            throw new RuntimeException(path);
        }
    }
}

