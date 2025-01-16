package browsersetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartBrowser {

    private static final Logger logger = LoggerFactory.getLogger(StartBrowser.class);
    private WebDriver driver;

    public WebDriver startBrowser(String browserName) {
        try {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver",
                            "C:\\Users\\yebabu\\Automation_Framework\\DemoAppHFM\\src\\test\\resources\\Driver\\chromedriver.exe");
                    logger.info("ChromeDriver path set.");
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    logger.info("Chrome options set.");
                    driver = new ChromeDriver(chromeOptions);
                    logger.info("ChromeDriver instance created.");
                    break;
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
                    logger.info("GeckoDriver path set.");
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    logger.info("Firefox options set.");
                    driver = new FirefoxDriver(firefoxOptions);
                    logger.info("FirefoxDriver instance created.");
                    break;
                case "safari":
                    driver = new SafariDriver();
                    logger.info("SafariDriver instance created.");
                    break;
                case "headless":
                    System.setProperty("webdriver.chrome.driver",
                            "C:\\Users\\yebabu\\Automation_Framework\\DemoAppHFM\\src\\test\\resources\\Driver\\chromedriver.exe");
                    logger.info("ChromeDriver path set for headless mode.");
                    ChromeOptions headlessOptions = new ChromeOptions();
                    headlessOptions.addArguments("--headless");
                    headlessOptions.addArguments("--disable-gpu");
                    headlessOptions.addArguments("--disable-web-security");
                    headlessOptions.addArguments("--remote-allow-origins=*");
                    logger.info("Headless Chrome options set.");
                    driver = new ChromeDriver(headlessOptions);
                    logger.info("Headless ChromeDriver instance created.");
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browserName);
            }
        } catch (Exception e) {
            logger.error("An error occurred while opening the browser: {}", browserName, e);
        }
        return driver;
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}