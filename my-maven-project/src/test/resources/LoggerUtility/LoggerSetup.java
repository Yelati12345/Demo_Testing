import java.io.IOException;
import java.util.logging.*;

public class LoggerSetup {

    private static final Logger logger = Logger.getLogger(LoggerSetup.class.getName());

    public static void setupLogger() {
        try {
            // Create a file handler that writes log messages to a file
            FileHandler fileHandler = new FileHandler("execution.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            // Add the file handler to the logger
            logger.addHandler(fileHandler);

            // Set the logger level to INFO
            logger.setLevel(Level.INFO);

            // Remove the console handler to prevent logging to the console
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers[0] instanceof ConsoleHandler) {
                rootLogger.removeHandler(handlers[0]);
            }

            // Add a custom console handler to only print starting and step messages
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFilter(record -> record.getMessage().contains("Starting") || record.getMessage().contains("step"));
            consoleHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(consoleHandler);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error setting up logger", e);
        }
    }

    public static void main(String[] args) {
        setupLogger();

        // Example log messages
        logger.info("Chrome options set.");
        logger.info("Starting ChromeDriver 131.0.6778.204 (52183f9e99a61056f9b78535f53d256f1516f2a0-refs/branch-heads/6778_155@{#7}) on port 56386");
        logger.info("Only local connections are allowed.");
        logger.info("Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.");
        logger.info("ChromeDriver was started successfully on port 56386.");
        logger.info("Jan 16, 2025 5:47:16 AM org.openqa.selenium.remote.ProtocolHandshake createSession INFO: Detected dialect: W3C");
        logger.warning("Jan 16, 2025 5:47:17 AM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch WARNING: Unable to find an exact match for CDP version 131, so returning the closest version found: a no-op implementation");
        logger.info("Jan 16, 2025 5:47:17 AM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch INFO: Unable to find CDP implementation matching 131.");
        logger.warning("Jan 16, 2025 5:47:17 AM org.openqa.selenium.chromium.ChromiumDriver lambda$new$3 WARNING: Unable to find version of CDP to use for . You may need to include a dependency on a specific version of the CDP using something similar to `org.seleniumhq.selenium:selenium-devtools-v86:4.1.0` where the version (\"v86\") matches the version of the chromium-based browser you're using and the version number of the artifact is the same as Selenium's.");
        logger.info("ChromeDriver instance created");
    }
}