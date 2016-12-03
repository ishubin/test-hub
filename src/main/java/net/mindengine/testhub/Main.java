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
package net.mindengine.testhub;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.mysql.cj.jdbc.MysqlDataSource;
import net.mindengine.testhub.controllers.TestsController;
import net.mindengine.testhub.controllers.api.FileApiController;
import net.mindengine.testhub.controllers.api.JobsApiController;
import net.mindengine.testhub.controllers.api.ProjectsApiController;
import net.mindengine.testhub.controllers.api.TestsApiController;
import net.mindengine.testhub.controllers.jobs.JobsController;
import net.mindengine.testhub.jobs.DataCleanupJob;
import net.mindengine.testhub.properties.TestHubProperties;
import net.mindengine.testhub.repository.RepositoryProvider;
import net.mindengine.testhub.repository.SimpleRepositoryProvider;
import net.mindengine.testhub.repository.files.FileStorage;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;


public class Main {
    private ServiceProvider serviceProvider;
    private final String filesResourcePrefix;
    private final FileStorage fileStorage;
    public static final String FILES_RESOURCE_NAME = "files";

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

    private int serverPort = 8082;


    public Main(ServiceProvider serviceProvider, String filesResourceName, FileStorage fileStorage) {
        this.serviceProvider = serviceProvider;
        this.filesResourcePrefix = filesResourceName;
        this.fileStorage = fileStorage;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        TestHubProperties properties = new TestHubProperties();

        String externalLocation = makeDirs(properties.fileStoragePath());
        String fullFileStoragePath = makeDirs(externalLocation + File.separator + FILES_RESOURCE_NAME);

        FileStorage fileStorage = new LocalFileStorage(fullFileStoragePath);
        new Main(createServiceProvider(properties), FILES_RESOURCE_NAME, fileStorage)
            .withServerPort(properties.port())
            .startServer(externalLocation);
    }


    public void startServer(String externalLocation) {
        port(serverPort);
        staticFileLocation("/public");
        externalStaticFileLocation(externalLocation);
        new ProjectsApiController(serviceProvider.findProjectService());
        new JobsApiController(serviceProvider.findJobsService());
        new TestsApiController(serviceProvider.findTestService());
        new FileApiController(fileStorage, filesResourcePrefix);
        new JobsController(serviceProvider.findJobsService());
        new TestsController();

        scheduledExecutorService.scheduleAtFixedRate(
            new DataCleanupJob(serviceProvider.findJobsService()),
            1,
            5,
            TimeUnit.MINUTES
        );

    }

    private static ServiceProvider createServiceProvider(TestHubProperties properties) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        BoneCP masterPool = createBoneCP(properties.dbJdbcUrl(), properties.dbUser(), properties.dbPassword());
        BoneCP slavePool = createBoneCP(properties.dbJdbcUrl(), properties.dbUser(), properties.dbPassword());

        ProjectsRepository projectRepository = new JdbcProjectsRepository(masterPool, slavePool);
        TestsRepository testsRepository = new JdbcTestsRepository(masterPool, slavePool);
        JobsRepository jobsRepository = new JdbcJobsRepository(masterPool, slavePool);
        RepositoryProvider repositoryProvider = new SimpleRepositoryProvider(projectRepository, jobsRepository, testsRepository);

        ProjectService projectService = new ProjectServiceImpl(repositoryProvider);
        JobsService jobsService = new JobsServiceImpl(repositoryProvider);
        TestService testService = new TestServiceImpl(repositoryProvider);

        return new DefaultServiceProvider(projectService, jobsService, testService);
    }


    private static String makeDirs(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new RuntimeException("Couldn't create directory: " + dir.getAbsolutePath());
            }
        } else if (!dir.isDirectory()) {
            throw new RuntimeException("Not a directory: " + dir.getAbsolutePath());
        }
        return dir.getAbsolutePath();
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

    public Main withServerPort(int serverPort) {
        this.serverPort = serverPort;
        return this;
    }
}
