package org.example.app;

import org.example.app.dao.factory.DAOFactory;
import org.example.app.DTOS.ProductoDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        int db =DAOFactory.DERBY_JDBC; // DAOFactory.POSTGRES_JDBC;

        try {
            CrearEsquema.run(db);
            CargarDatos.run(db);

            ProductoDTO producto = DevolverMaxRecaudacion.run(db);
            if (producto != null) {
                System.out.println(producto);
            }
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
