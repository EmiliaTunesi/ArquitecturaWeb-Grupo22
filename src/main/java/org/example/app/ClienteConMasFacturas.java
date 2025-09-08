package org.example.app;

import org.example.dao.factory.DAOFactory;
import org.example.utils.ConnectionFactory;
import org.example.utils.PostgresSingletonConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteConMasFacturas {

    public static void run(int dbId) {
        boolean esPostgres = (dbId == DAOFactory.POSTGRES_JDBC);
        ConnectionFactory factory = DAOFactory.getConnectionFactory(dbId);

        String sql =
                "SELECT c.id_client, c.nombre, c.email, COUNT(f.id_client) AS cantidad_facturas " +
                        "FROM cliente c " +
                        "LEFT JOIN factura f ON c.id_client = f.id_client " +
                        "GROUP BY c.id_client, c.nombre, c.email " +
                        "ORDER BY cantidad_facturas DESC";

        try (Connection conn = esPostgres
                ? PostgresSingletonConnection.getConnection()
                : factory.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int idCliente = rs.getInt("id_client");

                String nombre = rs.getString("nombre");
                if (nombre == null) nombre = "";

                String email = rs.getString("email");
                if (email == null) email = "";

                int cantidad = rs.getInt("cantidad_facturas");

                System.out.println("\nLista ordenada de clientes:");
                System.out.println(idCliente + " - " + nombre + " - " + email + " - Facturas: " + cantidad);
            }

        } catch (SQLException e) {
            System.err.println("Error consultando clientes con más facturas: " + e.getMessage());
        }
    }
}
