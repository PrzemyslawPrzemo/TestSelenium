package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ThreadLocalWebDriver {
    private static final ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(ThreadLocalWebDriver::createWebDriver);
    private static final Logger logger = LogManager.getLogger(ThreadLocalWebDriver.class);

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }

    private static WebDriver createWebDriver() {
        logger.info("Creating new WebDriver instance");
        String browser = System.getProperty("browser", "chrome");
        String resolution = System.getProperty("resolution", "DESKTOP");
        WebDriver webDriver = null;

        try {
            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    webDriver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    webDriver = new EdgeDriver();
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-search-engine-choice-screen");
                    webDriver = new ChromeDriver(options);
                    break;
            }

            if ("DESKTOP".equalsIgnoreCase(resolution)) {
                webDriver.manage().window().maximize();
            } else {
                ScreenSize screenSize = ScreenSize.valueOf(resolution.toUpperCase());
                logger.info("Test running at resolution: {}x{}", screenSize.getWidth(), screenSize.getHeight());
                webDriver.manage().window().setSize(new Dimension(screenSize.getWidth(), screenSize.getHeight()));
            }
        } catch (Exception e) {
            logger.error("Error creating WebDriver instance: ", e);
            if (webDriver != null) {
                webDriver.quit();
            }
            throw new RuntimeException("Failed to create WebDriver instance", e);
        }

        return webDriver;
    }
}
