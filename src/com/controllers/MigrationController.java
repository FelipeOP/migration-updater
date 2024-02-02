package updater.src.com.controllers;

import java.util.Set;

import updater.src.com.models.Document;
import updater.src.com.repositories.LogDatabaseRepository;
import updater.src.com.repositories.UrmDatabaseRepository;

public class MigrationController {

    private LogDatabaseRepository logDatabaseRepository;
    private UrmDatabaseRepository urmDatabaseRepository;

    public MigrationController(
            LogDatabaseRepository logDatabaseRepository,
            UrmDatabaseRepository urmDatabaseRepository) {

        this.logDatabaseRepository = logDatabaseRepository;
        this.urmDatabaseRepository = urmDatabaseRepository;

    }

    public void init() {
        Set<Document> exportedDocumentsList = logDatabaseRepository.getExportedDocuments();
        exportedDocumentsList = urmDatabaseRepository.checkUploadedDocuments(exportedDocumentsList);
        logDatabaseRepository.updateDocumentsState(exportedDocumentsList);
    }
}
