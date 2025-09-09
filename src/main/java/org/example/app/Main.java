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
                    System.out.println("Conexión Derby cerrada");
                }
            }

            if (db == DAOFactory.POSTGRES_JDBC) {
                org.example.app.utils.PostgresSingletonConnection.closeConnection();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Ocurrió un problema durante la ejecución.");
        }

        System.out.println("App finalizada");
    }
}
