package updater.src.com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import updater.src.com.main.Log;

public abstract class Database {

    DatabaseCredentials credentials;
    Connection connection;
    PreparedStatement preparedStatement;

    protected Database(DatabaseCredentials credentials) {
        this.credentials = credentials;
    }

    abstract Connection connect() throws SQLException;

    public PreparedStatement getStatement() {
        return preparedStatement;
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        preparedStatement = connection.prepareStatement(
            query,
            ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_READ_ONLY
        );
        return preparedStatement;
    }

    public ResultSet executeQuery() throws SQLException {
        return preparedStatement.executeQuery();
    }

    public void commit() throws SQLException {
        connection.commit();
        Log.LOGGER.log(Level.INFO, "Transaction commited");
    }

}
