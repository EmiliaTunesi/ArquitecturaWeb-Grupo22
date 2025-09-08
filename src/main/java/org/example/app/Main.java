package org.example.app;

import org.example.app.dao.factory.DAOFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        int db = DAOFactory.POSTGRES_JDBC; // O DAOFactory.DERBY_JDBC

        try {
            CrearEsquema.run(db);
            CargarDatos.run(db);


            DevolverMaxRecaudacion.run(db);
            ClienteConMasFacturas.run(db);

            if (db == DAOFactory.DERBY_JDBC) {
                try {
                    java.sql.DriverManager.getConnection("jdbc:derby:;shutdown=true");
                } catch (SQLException e) {
                    System.out.println("Derby cerrado correctamente.");
                }
            }

            if (db == DAOFactory.POSTGRES_JDBC) {
                try (Connection conn = org.example.app.utils.PostgresSingletonConnection.getConnection()) {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                        System.out.println("Conexión PostgreSQL cerrada.");
                    }
                } catch (SQLException e) {
                    System.err.println("Error cerrando conexión PostgreSQL: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("App finalizada");
    }
}
