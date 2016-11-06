/*******************************************************************************
* Copyright 2016 Ivan Shubin http://galenframework.com
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
package net.mindengine.testhub;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.mysql.cj.jdbc.MysqlDataSource;
import net.mindengine.testhub.controllers.api.FileApiController;
import net.mindengine.testhub.controllers.api.JobsApiController;
import net.mindengine.testhub.controllers.api.ProjectsApiController;
import net.mindengine.testhub.controllers.api.TestsApiController;
import net.mindengine.testhub.controllers.jobs.JobsController;
import net.mindengine.testhub.repository.RepositoryProvider;
import net.mindengine.testhub.repository.SimpleRepositoryProvider;
import net.mindengine.testhub.repository.files.FileStorage;
import net.mindengine.testhub.repository.files.FilesRepository;
import net.mindengine.testhub.repository.files.JdbcFilesRepository;
import net.mindengine.testhub.repository.files.LocalFileStorage;
import net.mindengine.testhub.repository.jobs.JdbcJobsRepository;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.projects.JdbcProjectsRepository;
import net.mindengine.testhub.repository.tests.JdbcTestsRepository;
import net.mindengine.testhub.services.*;
import org.flywaydb.core.Flyway;

import java.io.File;
import java.sql.SQLException;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.staticFileLocation;


public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String jdbcUrl = "jdbc:mysql://localhost/test_hub?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        BoneCP masterPool = createBoneCP(jdbcUrl, "root", "root123");
        BoneCP slavePool = createBoneCP(jdbcUrl, "root", "root123");

        String fileStoragePath = createStorageInDir("/opt/test-hub-storage");
        staticFileLocation("/public");
        externalStaticFileLocation(fileStoragePath);

        FileStorage fileStorage = new LocalFileStorage(fileStoragePath);

        ProjectsRepository projectRepository = new JdbcProjectsRepository(masterPool, slavePool);
        TestsRepository testsRepository = new JdbcTestsRepository(masterPool, slavePool);
        JobsRepository jobsRepository = new JdbcJobsRepository(masterPool, slavePool);
        FilesRepository filesRepository = new JdbcFilesRepository(masterPool, slavePool);
        RepositoryProvider repositoryProvider = new SimpleRepositoryProvider(projectRepository, jobsRepository, testsRepository, filesRepository);

        ProjectService projectService = new ProjectServiceImpl(repositoryProvider);
        JobsService jobsService = new JobsServiceImpl(repositoryProvider);
        TestService testService = new TestServiceImpl(repositoryProvider);
        FileService fileService = new FileServiceImpl(repositoryProvider);

        new ProjectsApiController(projectService);
        new JobsApiController(jobsService);
        new TestsApiController(testService);
        new FileApiController(fileService, fileStorage);
        new JobsController(jobsService);

    }

    private static String createStorageInDir(String path) {
        File storageDir = new File(path + File.separator + "storage");
        if (!storageDir.exists()) {
            if (!storageDir.mkdir()) {
                throw new RuntimeException("Could create directory: " + storageDir.getAbsolutePath());
            }
        } else if (!storageDir.isDirectory()) {
            throw new RuntimeException("Not a directory: " + storageDir.getAbsolutePath());
        }
        return storageDir.getAbsolutePath();
    }

    private static BoneCP createBoneCP(String jdbcUrl, String user, String password) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();

        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(password);
        return new BoneCP(config);
    }
}
