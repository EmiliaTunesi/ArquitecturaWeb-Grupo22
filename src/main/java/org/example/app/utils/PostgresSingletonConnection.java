package org.example.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresSingletonConnection {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_NAME = "arquitectura_db";
    private static final String URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;
    private static final String USER = "postgres";
    private static final String PASSWORD = "41146697db";

    private static Connection connection;

    private PostgresSingletonConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            crearBaseSiNoExiste();
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            connection.setAutoCommit(false);

        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();

            } catch (SQLException e) {

            } finally {
                connection = null;
            }
        }
    }

    private static void crearBaseSiNoExiste() {
        try (Connection conn = DriverManager.getConnection(DEFAULT_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + DB_NAME + "'")) {

            if (!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
                System.out.println("Base de datos '" + DB_NAME + "' creada.");
            }

        } catch (SQLException e) {
            System.err.println("Error verificando/creando la base de datos: " + e.getMessage());
        }
    }
}

