package updater.src.com.main;

import java.io.IOException;
import java.util.Properties;

import updater.src.com.controllers.MigrationController;
import updater.src.com.database.DatabaseConstants;
import updater.src.com.database.DatabaseCredentials;
import updater.src.com.database.OracleDatabase;
import updater.src.com.repositories.LogDatabaseRepository;
import updater.src.com.repositories.UrmDatabaseRepository;

public class App {
        public static void main(String[] args) throws IOException {
                Log.LOGGER.info("Starting application...");
                Properties properties = Configuration.readPropertiesFile(args[0]);
                Configuration.setGlobalProperties(properties);
                
                DatabaseCredentials logDatabaseCredentials = new DatabaseCredentials(
                                properties.getProperty(DatabaseConstants.LOG_STRING_URL),
                                properties.getProperty(DatabaseConstants.LOG_USERNAME),
                                properties.getProperty(DatabaseConstants.LOG_PASSWORD));
                DatabaseCredentials urmDatabaseCredentials = new DatabaseCredentials(
                                properties.getProperty(DatabaseConstants.UMR_URL),
                                properties.getProperty(DatabaseConstants.URM_USERNAME),
                                properties.getProperty(DatabaseConstants.URM_PASSWORD));

                OracleDatabase logDatabase = new OracleDatabase(logDatabaseCredentials);
                OracleDatabase urmDatabase = new OracleDatabase(urmDatabaseCredentials);

                MigrationController migrationController = new MigrationController(
                                new LogDatabaseRepository(logDatabase),
                                new UrmDatabaseRepository(urmDatabase));

                migrationController.init();
                Log.LOGGER.info("Application finished");
        }

}
