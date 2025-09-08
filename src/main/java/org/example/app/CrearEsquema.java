package org.example.app;

import org.example.dao.factory.DAOFactory;
import org.example.utils.ConnectionFactory;
import org.example.utils.PostgresSingletonConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearEsquema {

    public static void run(int dbId) {
        boolean esPostgres = (dbId == DAOFactory.POSTGRES_JDBC);

        try (Connection conn = esPostgres
                ? PostgresSingletonConnection.getConnection()
                : DAOFactory.getConnectionFactory(dbId).getConnection();
             Statement stmt = conn.createStatement()) {

            // Borrar tablas si existen
            try { stmt.execute("DROP TABLE factura_producto"); } catch (Exception ignored) {}
            try { stmt.execute("DROP TABLE factura"); } catch (Exception ignored) {}
            try { stmt.execute("DROP TABLE producto"); } catch (Exception ignored) {}
            try { stmt.execute("DROP TABLE cliente"); } catch (Exception ignored) {}

            // Crear tablas según DB
            String tableCliente, tableProducto, tableFactura, tableFacturaProducto;

            if (esPostgres) {
                tableCliente = "CREATE TABLE cliente(id_client SERIAL PRIMARY KEY, nombre VARCHAR(500), email VARCHAR(500))";
                tableProducto = "CREATE TABLE producto(id_producto SERIAL PRIMARY KEY, nombre VARCHAR(45), valor FLOAT)";
                tableFactura = "CREATE TABLE factura(id_factura SERIAL PRIMARY KEY, id_client INT REFERENCES cliente(id_client))";
                tableFacturaProducto = "CREATE TABLE factura_producto(id_factura INT REFERENCES factura(id_factura), id_producto INT REFERENCES producto(id_producto), cantidad INT, PRIMARY KEY(id_factura, id_producto))";
            } else {
                tableCliente = "CREATE TABLE cliente(idCliente INT PRIMARY KEY, nombre VARCHAR(500), email VARCHAR(500))";
                tableProducto = "CREATE TABLE producto(idProducto INT PRIMARY KEY, nombre VARCHAR(45), valor FLOAT)";
                tableFactura = "CREATE TABLE factura(idFactura INT PRIMARY KEY, idCliente INT REFERENCES cliente(idCliente))";
                tableFacturaProducto = "CREATE TABLE factura_producto(idFactura INT REFERENCES factura(idFactura), idProducto INT REFERENCES producto(idProducto), cantidad INT, PRIMARY KEY(idFactura, idProducto))";
            }

            stmt.execute(tableCliente);
            stmt.execute(tableProducto);
            stmt.execute(tableFactura);
            stmt.execute(tableFacturaProducto);

            System.out.println("✅ Esquema de tablas creado para DB ID: " + dbId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
