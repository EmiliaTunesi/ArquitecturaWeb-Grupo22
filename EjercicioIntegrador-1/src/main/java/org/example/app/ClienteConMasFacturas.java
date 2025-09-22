package org.example.app;

import org.example.app.dao.factory.DAOFactory;
import org.example.app.entity.Cliente;
import org.example.app.utils.ConnectionFactory;
import org.example.app.utils.PostgresSingletonConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteConMasFacturas {

    public static void run(int dbId) {
        boolean esPostgres = (dbId == DAOFactory.POSTGRES_JDBC);
        ConnectionFactory factory = DAOFactory.getConnectionFactory(dbId);

        String sql;
        if (esPostgres) {
            sql = "SELECT * FROM (" +
                    "    SELECT c.id_client, c.nombre, c.email, COUNT(f.id_client) AS cantidad_facturas " +
                    "    FROM cliente c " +
                    "    LEFT JOIN factura f ON c.id_client = f.id_client " +
                    "    GROUP BY c.id_client, c.nombre, c.email" +
                    ") AS sub " +
                    "ORDER BY cantidad_facturas DESC";

        } else {
            sql = "SELECT c.idCliente, c.nombre, c.email, COUNT(f.idCliente) AS cantidad_facturas " +
                    "FROM cliente c " +
                    "LEFT JOIN factura f ON c.idCliente = f.idCliente " +
                    "GROUP BY c.idCliente, c.nombre, c.email " +
                    "ORDER BY cantidad_facturas DESC";
        }

        try (Connection conn = esPostgres
                ? PostgresSingletonConnection.getConnection()
                : factory.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            System.out.println("\nLista ordenada de clientes:");
            while (rs.next()) {
                int idCliente = esPostgres ? rs.getInt("id_client") : rs.getInt("idCliente");

                String nombre = rs.getString("nombre");
                if (nombre == null) nombre = "";

                String email = rs.getString("email");
                if (email == null) email = "";

                int cantidad = rs.getInt("cantidad_facturas");

                Cliente cliente = new Cliente(idCliente,
                        nombre,email);

                System.out.println(cliente + " - Facturas: " + cantidad);
            }

        } catch (SQLException e) {
            System.err.println("Error consultando clientes con más facturas: " + e.getMessage());
        }
    }
}
