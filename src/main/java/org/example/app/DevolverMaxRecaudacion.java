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

        String sqlMaxRecaudacion;
        if (esPostgres) {
            sqlMaxRecaudacion =
                    "SELECT p.id_producto, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion " +
                            "FROM producto p " +
                            "JOIN factura_producto fp ON p.id_producto = fp.id_producto " +
                            "GROUP BY p.id_producto, p.nombre " +
                            "ORDER BY recaudacion DESC " +
                            "LIMIT 1";
        } else { //es derby
            sqlMaxRecaudacion =
                    "SELECT p.idProducto, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion " +
                            "FROM producto p " +
                            "JOIN factura_producto fp ON p.idProducto = fp.idProducto " +
                            "GROUP BY p.idProducto, p.nombre " +
                            "ORDER BY recaudacion DESC " +
                            "FETCH FIRST ROW ONLY";
        }

        try (Connection conn = esPostgres
                ? PostgresSingletonConnection.getConnection()
                : factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlMaxRecaudacion);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new ProductoDTO(
                        rs.getString("nombre"),
                        rs.getDouble("recaudacion")
                );
            } else {
                System.out.println("No hay productos vendidos.");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error consultando producto con mayor recaudación: " + e.getMessage());
            return null;
        }
    }
}
