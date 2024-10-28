package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BrowserActions {
    private static final Logger logger = LogManager.getLogger(BrowserActions.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToUrl(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        return title;
    }

    public String getAlertText() {
        logger.info("Getting alert text");
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }

    public void acceptAlert() {
        logger.info("Accepting alert");
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public boolean isAlertPresent() {
        boolean isPresent;
        try {
            driver.switchTo().alert();
            isPresent = true;
        } catch (NoAlertPresentException e) {
            isPresent = false;
        }
        logger.info("Alert present: {}", isPresent);
        return isPresent;
    }

    public void waitForUrlToContain(String urlPart) {
        logger.info("Waiting for URL to contain: {}", urlPart);
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    public void refreshPage() {
        logger.info("Refreshing the page");
        driver.navigate().refresh();
    }

    public void deleteAllCookies() {
        logger.info("Deleting all cookies");
        driver.manage().deleteAllCookies();
    }
}