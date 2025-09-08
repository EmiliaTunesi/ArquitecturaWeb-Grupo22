package org.example.app.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresConnectionFactory implements ConnectionFactory {

    @Override
    public Connection getConnection() throws SQLException {
        return PostgresSingletonConnection.getConnection();
    }
}