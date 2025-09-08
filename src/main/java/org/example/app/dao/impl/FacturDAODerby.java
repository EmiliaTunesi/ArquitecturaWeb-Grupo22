package org.example.app.dao.impl;

import org.example.app.dao.interfaces.FacturaDAO;
import org.example.app.entity.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FacturDAODerby implements FacturaDAO {
    private final Connection conn;

    public FacturDAODerby(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void insertar(Factura fac) {
        try {
            String sql = "INSERT INTO factura (idFactura, idCliente) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fac.getIdFacture());
            ps.setInt(2, fac.getIdClient());
            ps.executeUpdate();
            ps.close();
            conn.commit();
        } catch (SQLException e) {
            System.out.print(e + "Error");
        }
    }

    @Override
    public Factura obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<Factura> obtenerTodos() {
        return List.of();
    }

    @Override
    public void actualizar(Factura fac) {

    }

    @Override
    public void eliminar(int id) {

    }
}
