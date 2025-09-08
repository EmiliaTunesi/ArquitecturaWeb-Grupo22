package org.example.app.dao.impl;

import org.example.app.dao.interfaces.ClienteDAO;
import org.example.app.entity.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienDAODerby implements ClienteDAO {
    private final Connection conn;

    public ClienDAODerby (Connection con){
        this.conn = con;
    }

    @Override
    public void insertar(Cliente c) {
        try {
            String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c.getIdClient());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getEmail());
            ps.executeUpdate();
            ps.close();
            conn.commit();
        } catch (SQLException e) {
            System.out.print(e + "Error");
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT idCliente, nombre, email FROM CLIENTE";

        try (
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdClient(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombre"));
                c.setEmail(rs.getString("email"));
                clientes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }


    @Override
    public void actualizar(Cliente client) {

    }

    @Override
    public void eliminar(int id) {

    }
}
