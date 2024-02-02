package updater.src.com.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import updater.src.com.database.OracleDatabase;
import updater.src.com.main.Log;
import updater.src.com.models.Document;

public class LogDatabaseRepository {

    private OracleDatabase logDatabase;

    public LogDatabaseRepository(OracleDatabase logDatabase) {
        this.logDatabase = logDatabase;
    }

    public Set<Document> getExportedDocuments() {
        Log.LOGGER.log(Level.INFO, "Getting exported documents from log database...");

        String sqlQuery = "SELECT RECID, RUTA_IPM, NOMBRE_DOCUMENTO, RUTA_RESULTADO, ESTADO_IPM, ESTADO_OWCC, FECHA_PROCESO"
                + " FROM CLARO_UCM.PAGINACION_CORRESPONDENCIA"
                + " WHERE ESTADO_IPM = 'IPM_EXPORTADO' AND ESTADO_OWCC = ?";
      
        String requiredOwccState = "PQR-REPROCESO";
        Set<Document> documentsList = new HashSet<>();
        try (Connection connection = logDatabase.connect()) {
            logDatabase.prepareStatement(sqlQuery);
            logDatabase.getStatement().setString(1, requiredOwccState);
            logDatabase.getStatement().setFetchSize(1000);
            ResultSet resultSet = logDatabase.executeQuery();
            while (resultSet.next()) {
                Document document = new Document();
                document.setRecid(resultSet.getString("RECID"));
                document.setImpRoute(resultSet.getString("RUTA_IPM"));
                document.setDocumentName(resultSet.getString("NOMBRE_DOCUMENTO"));
                document.setPathResult(resultSet.getString("RUTA_RESULTADO"));
                document.setIpmState(resultSet.getString("ESTADO_IPM"));
                document.setOwccState(resultSet.getString("ESTADO_OWCC"));
                document.setProcessDate(resultSet.getDate("FECHA_PROCESO").toLocalDate());
                documentsList.add(document);
                Log.LOGGER.log(Level.INFO, "Documents added: {0}", document.getRecid());
            }
        } catch (SQLException | NullPointerException e) {
            Log.LOGGER.severe(e.getMessage());
        }
        Log.LOGGER.log(Level.INFO, "Documents found: {0}", documentsList.size());
        return documentsList;
    }

    public void updateDocumentsState(Set<Document> exportedDocumentsList) {
        Log.LOGGER.log(Level.INFO, "Updating documents state in log database...");

        String sqlQuery = "UPDATE CLARO_UCM.PAGINACION_CORRESPONDENCIA"
                + " SET ESTADO_OWCC = ?"
                + " WHERE RECID = ?";

        try (Connection connection = logDatabase.connect()) {
            logDatabase.prepareStatement(sqlQuery);
            for (Document document : exportedDocumentsList) {
                logDatabase.getStatement().setString(1, document.getOwccState());
                logDatabase.getStatement().setString(2, document.getRecid());
                logDatabase.executeQuery();
                Log.LOGGER.log(Level.INFO, "Updating document {0} - {1} rows updated",
                        new String[] {
                                document.getRecid(),
                                String.valueOf(logDatabase.getStatement().getUpdateCount())
                        });
                logDatabase.commit();
            }
        } catch (SQLException | NullPointerException e) {
            Log.LOGGER.severe(e.getMessage());
        }
        Log.LOGGER.log(Level.INFO, "Documents updated: {0}", exportedDocumentsList.size());
    }

}
