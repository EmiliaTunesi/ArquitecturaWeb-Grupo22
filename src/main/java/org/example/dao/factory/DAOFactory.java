package org.example.dao.factory;

import org.example.utils.ConnectionFactory;
import org.example.utils.DerbyConnectionFactory;
import org.example.utils.PostgresConnectionFactory;

import java.sql.SQLException;

public abstract class DAOFactory {

    public static final int DERBY_JDBC = 1;
    public static final int POSTGRES_JDBC = 2;

    public abstract org.example.dao.interfaces.ClienteDAO getClientDAO() throws SQLException;
    public abstract org.example.dao.interfaces.ProductoDAO getProductDAO() throws SQLException;
    public abstract org.example.dao.interfaces.FacturaDAO getFactureDAO() throws SQLException;
    public abstract org.example.dao.interfaces.FactProductoDAO getFacture_ProductDAO() throws SQLException;

    public static DAOFactory getDAOFactory(int db) {
        switch (db) {
            case DERBY_JDBC:
                return new DerbyDAOFactory();
            case POSTGRES_JDBC:
                return new PostgresDAOFactory();
            default:
                throw new IllegalArgumentException("DB no soportada: " + db);
        }
    }

    public static ConnectionFactory getConnectionFactory(int db) {
        switch (db) {
            case DERBY_JDBC:
                return new DerbyConnectionFactory();
            case POSTGRES_JDBC:
                return new PostgresConnectionFactory();
            default:
                throw new IllegalArgumentException("DB no soportada: " + db);
        }
    }
}

