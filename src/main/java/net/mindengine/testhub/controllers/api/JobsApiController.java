package net.mindengine.testhub.controllers.api;

import net.mindengine.testhub.model.jobs.Job;
import net.mindengine.testhub.model.jobs.JobResponse;
import net.mindengine.testhub.repository.jobs.JobsRepository;
import net.mindengine.testhub.repository.projects.ProjectsRepository;
import net.mindengine.testhub.repository.tests.TestsRepository;

import static java.util.stream.Collectors.toList;
import static spark.Spark.get;

public class JobsApiController extends ApiController {

    private final TestsRepository testsRepository;

    public JobsApiController(ProjectsRepository projectRepository, JobsRepository jobsRepository, TestsRepository testsRepository) {
        super(projectRepository, jobsRepository);
        this.testsRepository = testsRepository;
        init();
    }

    private void init() {
        getJson("/api/projects/:project/jobs", (req, res) -> {
            Long projectId = findMandatoryProject(req.params("project"));
            return jobsRepository.findAllJobsForProject(projectId).stream()
                .map(j ->
                    new JobResponse(j.getName())
                ).collect(toList());
        });
    }
}
