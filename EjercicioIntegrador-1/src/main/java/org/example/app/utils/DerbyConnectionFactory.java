package org.example.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyConnectionFactory implements ConnectionFactory {
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String URL = "jdbc:derby:integradorDB;create=true";

    static {
        try {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error cargando driver Derby", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
