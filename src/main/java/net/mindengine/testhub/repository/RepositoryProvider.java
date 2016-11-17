package net.mindengine.testhub.repository;

import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

public interface RepositoryProvider {

    ProjectsRepository projects();
    JobsRepository jobs();
    TestsRepository tests();
}
