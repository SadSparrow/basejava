package com.basejava.webapp.sql;

import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql, ABlockOfCode aBlockOfCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            aBlockOfCode.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface ABlockOfCode {
        void execute(PreparedStatement ps) throws SQLException;
    }
}
