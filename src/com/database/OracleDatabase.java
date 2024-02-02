package updater.src.com.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

import oracle.jdbc.pool.OracleDataSource;
import updater.src.com.main.Log;

public class OracleDatabase extends Database {

    public OracleDatabase(DatabaseCredentials credentials) {
        super(credentials);
    }

    @Override
    public Connection connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        OracleDataSource oracleDataSource = new OracleDataSource();
        oracleDataSource.setURL(credentials.getUrl());
        oracleDataSource.setUser(credentials.getUserName());
        oracleDataSource.setPassword(credentials.getPassword());
        connection = oracleDataSource.getConnection();
        connection.setAutoCommit(false);
        
        Log.LOGGER.log(Level.INFO, "{0} connected to Oracle database", connection.getSchema());
        return connection;
    }

}
