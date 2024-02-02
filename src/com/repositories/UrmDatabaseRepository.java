package updater.src.com.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;

import updater.src.com.database.OracleDatabase;
import updater.src.com.main.Log;
import updater.src.com.models.Document;

public class UrmDatabaseRepository {

    private OracleDatabase urmDatabase;

    public UrmDatabaseRepository(OracleDatabase urmDatabase) {
        this.urmDatabase = urmDatabase;
    }

    public Set<Document> checkUploadedDocuments(Set<Document> documentsList) {
        String sqlQuery = "SELECT D.DORIGINALNAME "
                + "FROM WCC_URMSERVER.REVISIONS R "
                + "INNER JOIN WCC_URMSERVER.DOCUMENTS D "
                + "ON D.DID = R.DID "
                + "AND D.DORIGINALNAME = ?"
                + "AND R.DCREATEDATE >= ?";
        
        String newOwccState = "PQR-EXITOSO";
        try (Connection connection = urmDatabase.connect()) {
            urmDatabase.prepareStatement(sqlQuery);
            for (Document document : documentsList) {
                urmDatabase.getStatement().setString(1, document.getDocumentName());
                urmDatabase.getStatement().setDate(2, Date.valueOf(document.getProcessDate()));
                ResultSet resultSet = urmDatabase.executeQuery();
                if (resultSet.next()) {
                    Log.LOGGER.log(Level.INFO, "Document {0} found", resultSet.getString("DORIGINALNAME"));
                    document.setOwccState(newOwccState);
                    continue;
                }
                Log.LOGGER.log(Level.INFO, "Document {0} not found", document.getDocumentName());
            }
        } catch (SQLException | NullPointerException e) {
            Log.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        documentsList.removeIf(document -> !document.getOwccState().equals(newOwccState));
        Log.LOGGER.log(Level.INFO, "Documents to update {0}", documentsList.size());
        return documentsList;
    }

}
