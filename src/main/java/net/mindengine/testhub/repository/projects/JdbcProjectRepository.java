package net.mindengine.testhub.repository.projects;

import com.jolbox.bonecp.BoneCP;
import net.mindengine.testhub.model.projects.Project;
import net.mindengine.testhub.repository.JdbcRepository;

public class JdbcProjectRepository extends JdbcRepository implements ProjectRepository {
    public JdbcProjectRepository(BoneCP connectionPool) {
        super(connectionPool);
    }

    @Override
    public Long createProject(Project project) {
        return insert("insert into projects (name) values (?)", project.getName());
    }
}
