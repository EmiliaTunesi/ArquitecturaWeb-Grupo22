package org.example.app;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.app.dao.factory.DAOFactory;
import org.example.app.dao.interfaces.ClienteDAO;
import org.example.app.dao.interfaces.ProductoDAO;
import org.example.app.dao.interfaces.FacturaDAO;
import org.example.app.dao.interfaces.FactProductoDAO;
import org.example.app.entity.Cliente;
import org.example.app.entity.Producto;
import org.example.app.entity.Factura;
import org.example.app.entity.FacturaProducto;
import org.example.app.utils.ConnectionFactory;
import org.example.app.utils.PostgresSingletonConnection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CargarDatos {

    public static void run(int dbId) {
        ConnectionFactory factory = DAOFactory.getConnectionFactory(dbId);

        try (Connection conn = factory.getConnection()) {

            DAOFactory daoFactory = DAOFactory.getDAOFactory(dbId);


            leerClientesDesdeCSV(daoFactory.getClientDAO(), "EjercicioIntegrador-1/src/main/resources/clientes.csv");

            leerProductosDesdeCSV(daoFactory.getProductDAO(), "EjercicioIntegrador-1/src/main/resources/productos.csv");

            leerFacturasDesdeCSV(daoFactory.getFactureDAO(), "EjercicioIntegrador-1/src/main/resources/facturas.csv");

            leerFacturaProductosDesdeCSV(daoFactory.getFacture_ProductDAO(), "EjercicioIntegrador-1/src/main/resources/facturas-productos.csv");

            conn.commit();

        } catch (Exception e) {
            e.getMessage();
        } finally {
            PostgresSingletonConnection.closeConnection();
        }
    }


    private static void leerClientesDesdeCSV(ClienteDAO dao, String path) throws IOException {

        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(path));
        int count = 0;

        for (CSVRecord row : parser) {
            String idClienteStr = row.get("idCliente");
            if (idClienteStr == null || idClienteStr.trim().isEmpty()) continue;

            String nombre = row.get("nombre");
            String email = row.get("email");

            try {
                Cliente c = new Cliente(
                        Integer.parseInt(idClienteStr.trim()),
                        nombre != null ? nombre.trim() : "",
                        email != null ? email.trim() : ""
                );

                dao.insertar(c);
                count++;

            } catch (NumberFormatException e) {
                System.err.println("[ERROR] Error parsing idCliente: " + idClienteStr);
            }
        }

    }

    private static void leerProductosDesdeCSV(ProductoDAO dao, String path) throws IOException {

        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(path));
        int count = 0;

        for (CSVRecord row : parser) {
            String idProductoStr = row.get("idProducto");
            if (idProductoStr == null || idProductoStr.trim().isEmpty()) continue;

            String nombre = row.get("nombre");
            String valorStr = row.get("valor");

            float valor = 0;
            if (valorStr != null && !valorStr.trim().isEmpty()) {
                try {
                    valor = Float.parseFloat(valorStr.trim());
                } catch (NumberFormatException e) {
                    System.err.println("[ERROR] Error parsing valor: " + valorStr);
                }
            }

            try {
                Producto p = new Producto(
                        Integer.parseInt(idProductoStr.trim()),
                        nombre != null ? nombre.trim() : "",
                        valor
                );
                dao.insertar(p);
                count++;

            } catch (NumberFormatException e) {
                System.err.println("[ERROR] Error parsing idProducto: " + idProductoStr);
            }
        }

    }

    private static void leerFacturasDesdeCSV(FacturaDAO dao, String path) throws IOException {

        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(path));
        int count = 0;

        for (CSVRecord row : parser) {
            String idFacturaStr = row.get("idFactura");
            String idClienteStr = row.get("idCliente");

            if (idFacturaStr == null || idFacturaStr.trim().isEmpty()) continue;

            int idFactura, idCliente = 0;
            try {
                idFactura = Integer.parseInt(idFacturaStr.trim());
                if (idClienteStr != null && !idClienteStr.trim().isEmpty())
                    idCliente = Integer.parseInt(idClienteStr.trim());

                Factura f = new Factura(idFactura, idCliente);
                dao.insertar(f);
                count++;
            } catch (NumberFormatException e) {
                System.err.println("[ERROR] Error parsing factura o cliente: " + idFacturaStr + ", " + idClienteStr);
            }
        }
    }

    private static void leerFacturaProductosDesdeCSV(FactProductoDAO dao, String path) throws IOException {

        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(path));
        int count = 0;

        for (CSVRecord row : parser) {
            String idFacturaStr = row.get("idFactura");
            String idProductoStr = row.get("idProducto");
            String cantidadStr = row.get("cantidad");

            if (idFacturaStr == null || idFacturaStr.trim().isEmpty() ||
                    idProductoStr == null || idProductoStr.trim().isEmpty()) continue;

            int idFactura = 0, idProducto = 0, cantidad = 0;
            try {
                idFactura = Integer.parseInt(idFacturaStr.trim());
                idProducto = Integer.parseInt(idProductoStr.trim());
                if (cantidadStr != null && !cantidadStr.trim().isEmpty())
                    cantidad = Integer.parseInt(cantidadStr.trim());

                FacturaProducto fp = new FacturaProducto(idFactura, idProducto, cantidad);
                dao.insertar(fp);
                count++;

            } catch (NumberFormatException e) {
                System.err.println("[ERROR] Error parsing factura_producto: " +
                        idFacturaStr + ", " + idProductoStr + ", " + cantidadStr);
            }
        }
    }
}

