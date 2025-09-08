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

import java.io.FileReader;
import java.io.IOException;

public class CargarDatos {

    public static void run(int dbId) {
        boolean esPostgres = (dbId == DAOFactory.POSTGRES_JDBC);

        try {
            DAOFactory daoFactory = DAOFactory.getDAOFactory(dbId);

            ClienteDAO clienteDAO = daoFactory.getClientDAO();
            ProductoDAO productoDAO = daoFactory.getProductDAO();
            FacturaDAO facturaDAO = daoFactory.getFactureDAO();
            FactProductoDAO facturaProductoDAO = daoFactory.getFacture_ProductDAO();

            leerClientesDesdeCSV(clienteDAO, "src/main/resources/clientes.csv");
            leerProductosDesdeCSV(productoDAO, "src/main/resources/productos.csv");
            leerFacturasDesdeCSV(facturaDAO, "src/main/resources/facturas.csv");
            leerFacturaProductosDesdeCSV(facturaProductoDAO, "src/main/resources/facturas-productos.csv");

            System.out.println("Datos cargados");

        } catch (Exception e) {
            System.err.println("Error cargando datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void leerClientesDesdeCSV(ClienteDAO dao, String path) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(path));

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
            } catch (NumberFormatException e) {
                System.err.println("Error parsing idCliente: " + idClienteStr);
            }
        }
    }

    private static void leerProductosDesdeCSV(ProductoDAO dao, String path) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(path));

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
                    System.err.println("Error parsing valor: " + valorStr);
                }
            }

            try {
                Producto p = new Producto(
                        Integer.parseInt(idProductoStr.trim()),
                        nombre != null ? nombre.trim() : "",
                        valor
                );
                dao.insertar(p);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing idProducto: " + idProductoStr);
            }
        }
    }

    private static void leerFacturasDesdeCSV(FacturaDAO dao, String path) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(path));

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

            } catch (NumberFormatException e) {
                System.err.println("Error parsing factura o cliente: " + idFacturaStr + ", " + idClienteStr);
            }
        }
    }

    private static void leerFacturaProductosDesdeCSV(FactProductoDAO dao, String path) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(path));

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

            } catch (NumberFormatException e) {
                System.err.println("Error parsing factura_producto: " +
                        idFacturaStr + ", " + idProductoStr + ", " + cantidadStr);
            }
        }
    }
}
