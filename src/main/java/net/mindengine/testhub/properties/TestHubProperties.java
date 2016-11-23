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
package net.mindengine.testhub.properties;

public class TestHubProperties {
    public String dbJdbcUrl() {
        return "jdbc:mysql://" + dbHost() + "/" + dbSchema() + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    }

    public String dbSchema() {
        return property("testhub.db.schema", "test_hub");
    }

    public String dbHost() {
        return property("testhub.db.host", "localhost:3306");
    }

    public String dbUser() {
        return property("testhub.db.user", "root");
    }

    public String dbPassword() {
        return property("testhub.db.password", "root123");
    }

    public String fileStoragePath() {
        return property("testhub.storage", "/opt/test-hub-storage");
    }

    private String property(String name, String defaultValue) {
        return System.getProperty(name, defaultValue);
    }
}
