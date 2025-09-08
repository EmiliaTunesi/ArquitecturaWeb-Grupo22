package org.example.dao.factory;

import org.example.dao.interfaces.ClienteDAO;
import org.example.dao.interfaces.FactProductoDAO;
import org.example.dao.interfaces.FacturaDAO;
import org.example.dao.interfaces.ProductoDAO;
import org.example.dao.implPostgres.ClienteDAOPostgres;
import org.example.dao.implPostgres.FacturaDAOPostgres;
import org.example.dao.implPostgres.FacturaProdDAOPostgres;
import org.example.dao.implPostgres.ProductoDAOPostgres;

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

