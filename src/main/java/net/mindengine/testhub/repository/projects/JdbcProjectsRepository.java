/*******************************************************************************
 * Copyright 2016 Ivan Shubin https://github.com/ishubin/dash-server
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
