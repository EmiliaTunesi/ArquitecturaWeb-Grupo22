package org.example.app.dao.impl;

import org.example.app.dao.interfaces.FactProductoDAO;
import org.example.app.entity.FacturaProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FacturProdDAODerby implements FactProductoDAO {
    private final Connection conn;

    public FacturProdDAODerby (Connection con){
        this.conn = con;
    }

    @Override
    public void insertar(FacturaProducto fac) {
        try {
            String sql = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fac.getIdFacture());
            ps.setInt(2, fac.getIdProduct());
            ps.setInt(3, fac.getCantidad());
            ps.executeUpdate();
            ps.close();
            conn.commit();
        } catch (SQLException e) {
            System.out.print(e + "Error");
        }
    }

    @Override
    public FacturaProducto obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<FacturaProducto> obtenerTodos() {
        return List.of();
    }

    @Override
    public void actualizar(FacturaProducto fac) {

    }

    @Override
    public void eliminar(int id) {

    }
}
