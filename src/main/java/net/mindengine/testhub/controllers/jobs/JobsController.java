package net.mindengine.testhub.controllers.jobs;

import net.mindengine.testhub.controllers.api.Controller;
import net.mindengine.testhub.repository.RepositoryProvider;

public class JobsController extends Controller {
    public JobsController(RepositoryProvider repositoryProvider) {
        super(repositoryProvider);
        init();
    }

    private void init() {
        getHsTpl("/projects/:project/jobs", "jobs.hbs", (req, model) -> {
        });
    }
}
