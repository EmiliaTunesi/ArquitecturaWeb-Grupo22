package org.example.app.dao.implPostgres;

import org.example.app.dao.interfaces.ClienteDAO;
import org.example.app.entity.Cliente;
import org.example.app.utils.PostgresSingletonConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOPostgres implements ClienteDAO {

    @Override
    public void insertar(Cliente c) {
        String sql = "INSERT INTO cliente (nombre, email) VALUES (?, ?)";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, c.getNombre());
                ps.setString(2, c.getEmail());
                ps.executeUpdate();


                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        c.setIdClient(rs.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT id_client, nombre, email FROM cliente WHERE id_client = ?";
        Cliente c = null;

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        c = new Cliente(rs.getInt("id_client"), rs.getString("nombre"), rs.getString("email"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_client, nombre, email FROM cliente";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    clientes.add(new Cliente(rs.getInt("id_client"), rs.getString("nombre"), rs.getString("email")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    @Override
    public void actualizar(Cliente c) {
        String sql = "UPDATE cliente SET nombre = ?, email = ? WHERE id_client = ?";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getEmail());
                ps.setInt(3, c.getIdClient());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM cliente WHERE id_client = ?";

        try {
            Connection conn = PostgresSingletonConnection.getConnection(); // no cerrar
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
