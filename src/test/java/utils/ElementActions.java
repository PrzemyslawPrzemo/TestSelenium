package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ElementActions {

    private static final Logger logger = LogManager.getLogger(ElementActions.class);
    private final WebDriver driver;
    private final WaitHelper waitHelper;
    private final Actions actions;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        this.actions = new Actions(driver);
    }

    public void click(WebElement element) {
        logger.debug("Clicking on element");
        WebElement webElement = waitHelper.waitForElementToBeClickable(element);
        scrollToElement(webElement);
        webElement.click();
        logger.debug("Element clicked successfully");
    }

    public void waitForTableToLoad(WebElement table) {
        waitHelper.waitForElementToBeVisible(table);
    }

    public void scrollToElement(WebElement element) {
        logger.debug("Scrolling to get the element into view");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToBottom() {
        logger.info("Starting to scroll to the bottom of the page");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long lastHeight = (Long) js.executeScript("return document.documentElement.scrollHeight");
        int unchangedScrolls = 0;
        final int MAX_UNCHANGED_SCROLLS = 10;

        while (unchangedScrolls < MAX_UNCHANGED_SCROLLS) {
            js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight)");
            Long newHeight = (Long) js.executeScript("return document.documentElement.scrollHeight");
            if (newHeight.equals(lastHeight)) {
                unchangedScrolls++;
            } else {
                unchangedScrolls = 0;
                lastHeight = newHeight;
            }
        }
        logger.info("Reached bottom of page");
    }
}
