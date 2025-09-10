package org.example.app;

import org.example.app.dao.factory.DAOFactory;
import org.example.app.utils.ConnectionFactory;
import org.example.app.utils.PostgresSingletonConnection;
import org.example.app.DTOS.ProductoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DevolverMaxRecaudacion {

    public static ProductoDTO run(int dbId) {
        boolean esPostgres = (dbId == DAOFactory.POSTGRES_JDBC);
        ConnectionFactory factory = DAOFactory.getConnectionFactory(dbId);

        String sqlMaxRecaudacion =
                "SELECT p.id_producto, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion " +
                        "FROM producto p " +
                        "JOIN factura_producto fp ON p.id_producto = fp.id_producto " +
                        "GROUP BY p.id_producto, p.nombre " +
                        "ORDER BY recaudacion DESC " +
                        "FETCH FIRST ROW ONLY";

        try (Connection conn = esPostgres
                ? PostgresSingletonConnection.getConnection()
                : factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlMaxRecaudacion);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new ProductoDTO(
                        rs.getString("nombre"),
                        rs.getDouble("totalRecaudado")
                );
            }

           /* if (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                if (nombre == null) nombre = "";
                double recaudacion = rs.getDouble("recaudacion");

                System.out.println("\nProducto que más recaudó:");
                System.out.println(idProducto + " - " + nombre + " - Recaudación: " + recaudacion);
            } */else {
                System.out.println("No hay productos vendidos.");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error consultando producto con mayor recaudación: " + e.getMessage());
            return null;
        }
    }
}
