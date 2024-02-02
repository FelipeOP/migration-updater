package updater.src.com.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

import org.h2.jdbcx.JdbcConnectionPool;

import updater.src.com.main.Log;

public class H2Database extends Database {

    public H2Database(DatabaseCredentials credentials) {
        super(credentials);
    }

    @Override
    public Connection connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create(
                credentials.getUrl(),
                credentials.getUserName(),
                credentials.getPassword());

        connection = connectionPool.getConnection();
        connection.setAutoCommit(false);
        Log.LOGGER.log(Level.INFO, "{0} connected to H2 database", connection.getSchema());
        return connection;
    }

}
