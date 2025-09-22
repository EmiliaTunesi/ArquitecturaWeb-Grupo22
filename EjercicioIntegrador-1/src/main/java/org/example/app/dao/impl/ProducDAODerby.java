package org.example.app.dao.impl;

import org.example.app.dao.interfaces.ProductoDAO;
import org.example.app.entity.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProducDAODerby implements ProductoDAO {
    private final Connection conn;

    public ProducDAODerby (Connection con){
        this.conn = con;
    }

    @Override
    public void insertar(Producto p) {
        try {
            String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getIdProduct());
            ps.setString(2, p.getNombre());
            ps.setFloat(3, p.getValue());
            ps.executeUpdate();
            ps.close();
            conn.commit();
        } catch (SQLException e) {
            System.out.print(e + "Error");
        }
    }

    @Override
    public Producto obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<Producto> obtenerTodos() {
        return List.of();
    }

    @Override
    public void actualizar(Producto p) {

    }

    @Override
    public void eliminar(int id) {

    }
}
