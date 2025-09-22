package org.example.app.dao.implPostgres;

import org.example.app.dao.interfaces.FacturaDAO;
import org.example.app.entity.Factura;
import org.example.app.utils.PostgresSingletonConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOPostgres implements FacturaDAO {

    public FacturaDAOPostgres() {}

    @Override
    public void insertar(Factura fac) {
        String sql = "INSERT INTO factura (id_client) VALUES (?) RETURNING id_factura";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, fac.getIdClient()); // solo id_client, la PK la genera DB

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        fac.setIdFacture(rs.getInt(1)); // actualizar id generado
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error insertando factura: " + e.getMessage());
        }
    }

    @Override
    public Factura obtenerPorId(int id) {
        String sql = "SELECT id_factura, id_client FROM factura WHERE id_factura = ?";
        Factura fac = null;

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        fac = new Factura(rs.getInt("id_factura"), rs.getInt("id_client"));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error obteniendo factura: " + e.getMessage());
        }

        return fac;
    }

    @Override
    public List<Factura> obtenerTodos() {
        String sql = "SELECT id_factura, id_client FROM factura";
        List<Factura> facturas = new ArrayList<>();

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    facturas.add(new Factura(rs.getInt("id_factura"), rs.getInt("id_client")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error obteniendo todas las facturas: " + e.getMessage());
        }

        return facturas;
    }

    @Override
    public void actualizar(Factura fac) {
        String sql = "UPDATE factura SET id_client = ? WHERE id_factura = ?";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, fac.getIdClient());
                ps.setInt(2, fac.getIdFacture());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error actualizando factura: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM factura WHERE id_factura = ?";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error eliminando factura: " + e.getMessage());
        }
    }
}
