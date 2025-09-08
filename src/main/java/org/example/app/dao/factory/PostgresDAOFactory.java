package org.example.app.dao.factory;

import org.example.app.dao.interfaces.ClienteDAO;
import org.example.app.dao.interfaces.FactProductoDAO;
import org.example.app.dao.interfaces.FacturaDAO;
import org.example.app.dao.interfaces.ProductoDAO;
import org.example.app.dao.implPostgres.ClienteDAOPostgres;
import org.example.app.dao.implPostgres.FacturaDAOPostgres;
import org.example.app.dao.implPostgres.FacturaProdDAOPostgres;
import org.example.app.dao.implPostgres.ProductoDAOPostgres;

public class PostgresDAOFactory extends DAOFactory {

    @Override
    public ClienteDAO getClientDAO() {
        return new ClienteDAOPostgres();
    }

    @Override
    public FacturaDAO getFactureDAO() {
        return new FacturaDAOPostgres();
    }

    @Override
    public ProductoDAO getProductDAO() {
        return new ProductoDAOPostgres();
    }

    @Override
    public FactProductoDAO getFacture_ProductDAO() {
        return new FacturaProdDAOPostgres();
    }
}

