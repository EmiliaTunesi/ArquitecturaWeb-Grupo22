package org.example.app.dao.factory;

import org.example.app.utils.ConnectionFactory;
import org.example.app.utils.DerbyConnectionFactory;
import org.example.app.utils.PostgresConnectionFactory;

import java.sql.SQLException;

public abstract class DAOFactory {

    public static final int DERBY_JDBC = 1;
    public static final int POSTGRES_JDBC = 2;

    public abstract org.example.app.dao.interfaces.ClienteDAO getClientDAO() throws SQLException;
    public abstract org.example.app.dao.interfaces.ProductoDAO getProductDAO() throws SQLException;
    public abstract org.example.app.dao.interfaces.FacturaDAO getFactureDAO() throws SQLException;
    public abstract org.example.app.dao.interfaces.FactProductoDAO getFacture_ProductDAO() throws SQLException;

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

