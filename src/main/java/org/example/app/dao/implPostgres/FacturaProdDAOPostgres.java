package org.example.app.dao.implPostgres;

import org.example.app.dao.interfaces.FactProductoDAO;
import org.example.app.entity.FacturaProducto;
import org.example.app.utils.PostgresSingletonConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaProdDAOPostgres implements FactProductoDAO {

    public FacturaProdDAOPostgres() {}

    @Override
    public void insertar(FacturaProducto fac) {
        String sql = """
            INSERT INTO factura_producto (id_factura, id_producto, cantidad)
            VALUES (?, ?, ?)
            ON CONFLICT (id_factura, id_producto)
            DO UPDATE SET cantidad = EXCLUDED.cantidad
            """;

        try {
            Connection conn = PostgresSingletonConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, fac.getIdFacture());
                ps.setInt(2, fac.getIdProduct());
                ps.setInt(3, fac.getCantidad());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error insertando factura_producto: " + e.getMessage());
        }
    }

    @Override
    public FacturaProducto obtenerPorId(int id) {
        // Mantiene la interfaz original (solo busca por id_factura)
        String sql = "SELECT id_factura, id_producto, cantidad FROM factura_producto WHERE id_factura = ? LIMIT 1";
        FacturaProducto fp = null;

        try {
            Connection conn = PostgresSingletonConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        fp = new FacturaProducto(
                                rs.getInt("id_factura"),
                                rs.getInt("id_producto"),
                                rs.getInt("cantidad")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo factura_producto: " + e.getMessage());
        }

        return fp;
    }


    @Override
    public List<FacturaProducto> obtenerTodos() {
        String sql = "SELECT id_factura, id_producto, cantidad FROM factura_producto";
        List<FacturaProducto> lista = new ArrayList<>();

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(new FacturaProducto(
                            rs.getInt("id_factura"),
                            rs.getInt("id_producto"),
                            rs.getInt("cantidad")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error obteniendo todas las factura_producto: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public void actualizar(FacturaProducto fac) {
        String sql = "UPDATE factura_producto SET cantidad = ? WHERE id_factura = ? AND id_producto = ?";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, fac.getCantidad());
                ps.setInt(2, fac.getIdFacture());
                ps.setInt(3, fac.getIdProduct());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error actualizando factura_producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM factura_producto WHERE id_factura = ?";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error eliminando factura_producto: " + e.getMessage());
        }
    }
}
