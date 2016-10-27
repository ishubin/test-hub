package net.mindengine.testhub.repository.projects;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.model.projects.Project;
import net.mindengine.testhub.repository.JdbcRepository;

import java.util.Optional;

public class JdbcProjectsRepository extends JdbcRepository implements ProjectsRepository {
    public JdbcProjectsRepository(BoneCP masterPool, BoneCP slavePool) {
        super(masterPool, slavePool);
    }

    @Override
    public Long createProject(Project project) {
        return insert("insert into projects (name) values (?)", project.getName());
    }

    @Override
    public Optional<Long> findProjectIdByName(String projectName) {
        return query("select project_id from projects where name = ?", projectName).singleLong();
    }
}
