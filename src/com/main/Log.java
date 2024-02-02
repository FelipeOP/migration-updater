package updater.src.com.main;

import java.util.Locale;
import java.util.logging.Logger;

public class Log {

    public static final Logger LOGGER;

    private static final String FORMATTER = "java.util.logging.SimpleFormatter.format";
    private static final String FORMAT = "[%1$tF %1$tT %1$Tp] [%2$s]%n[%4$s]: %5$s %n";

    Log() {
    }

    static {
        Locale.setDefault(new Locale("en", "US"));
        System.setProperty(FORMATTER, FORMAT);
        LOGGER = Logger.getLogger(Log.class.getName());
    }

    public static void progress(String message) {
        System.out.printf("%s\r", message);
    }
}
