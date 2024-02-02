package updater.src.com.main;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    Configuration() {
    }

    private static Properties globalProperties;

    public static String getProperty(String key) {
        return globalProperties.getProperty(key);
    }

    public static void setGlobalProperties(Properties properties) {
        globalProperties = properties;
    }

    public static Properties readPropertiesFile(String filePath) throws IOException {
        Log.LOGGER.info("Reading properties file...");
        try (FileReader reader = new FileReader(filePath)) {
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        }
    }
}
