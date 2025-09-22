package org.example.app.dao.implPostgres;

import org.example.app.dao.interfaces.ProductoDAO;
import org.example.app.entity.Producto;
import org.example.app.utils.PostgresSingletonConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOPostgres implements ProductoDAO {

    public ProductoDAOPostgres() {}

    @Override
    public void insertar(Producto p) {
        String sql = "INSERT INTO producto (nombre, valor) VALUES (?, ?) RETURNING id_producto";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, p.getNombre());
                ps.setFloat(2, p.getValue());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        p.setIdProduct(rs.getInt(1)); // ID generado automáticamente
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error insertando producto: " + e.getMessage());
        }
    }


    @Override
    public Producto obtenerPorId(int id) {
        String sql = "SELECT id_producto, nombre, valor FROM producto WHERE id_producto = ?";
        Producto prod = null;

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        prod = new Producto(
                                rs.getInt("id_producto"),
                                rs.getString("nombre"),
                                rs.getFloat("valor")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error obteniendo producto: " + e.getMessage());
        }

        return prod;
    }

    @Override
    public List<Producto> obtenerTodos() {
        String sql = "SELECT id_producto, nombre, valor FROM producto";
        List<Producto> lista = new ArrayList<>();

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getFloat("valor")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error obteniendo todos los productos: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public void actualizar(Producto p) {
        String sql = "UPDATE producto SET nombre = ?, valor = ? WHERE id_producto = ?";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getNombre());
                ps.setFloat(2, p.getValue());
                ps.setInt(3, p.getIdProduct());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error actualizando producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error eliminando producto: " + e.getMessage());
        }
    }
}
