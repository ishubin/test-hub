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
package net.mindengine.testhub.repository;

import com.jolbox.bonecp.BoneCP;

import java.sql.*;

public class JdbcRepository {
    private final BoneCP masterPool;
    private final BoneCP slavePool;

    interface StatementConsumer<R> {
        R accept(Statement statement) throws SQLException;
    }

    public interface SqlConsumer<R> {
        R accept(Statement statement, ResultSet resultSet) throws SQLException;
    }

    public JdbcRepository(BoneCP masterPool, BoneCP slavePool) {
        this.masterPool = masterPool;
        this.slavePool = slavePool;
    }

    public ResultSetMapper query(String sql, Object... args) {
        try {
            Connection connection = slavePool.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            try {
                return new ResultSetMapper(statement.executeQuery());
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error executing statement", ex);
        }
    }

    private <R> R executeStatement(String sql, Object[] args, StatementConsumer<R> consumer) {
        try {
            Connection connection = masterPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            try {
                statement.execute();
                return consumer.accept(statement);
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error executing statement", ex);
        }
    }

    public Long insert(String sql, Object... args) {
       return executeStatement(sql, args, (statement) -> {
            ResultSet rsKeys = statement.getGeneratedKeys();
            if (rsKeys.next()) {
                return rsKeys.getLong(1);
            } else {
                return 0L;
            }
       });
    }

    public void update(String sql, Object... args) {
        executeStatement(sql, args, (statement) -> {
            statement.getGeneratedKeys();
            return null;
        });
    }

}
