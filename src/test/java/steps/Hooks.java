package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.BrowserActions;
import utils.TestRecorder;
import utils.ThreadLocalWebDriver;
import utils.TimestampGenerator;

import java.io.FileInputStream;
import java.io.IOException;

import static utils.ScreenshotHelper.takeScreenshot;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private final BrowserActions browserActions;
    private final TestRecorder testRecorder;
    private final WebDriver driver;
    private String timestamp;

    public Hooks() {
        this.driver = ThreadLocalWebDriver.getDriver();
        this.browserActions = new BrowserActions(driver);
        this.testRecorder = new TestRecorder();
    }

    @Before
    public void setUp(Scenario scenario) {
        timestamp = TimestampGenerator.generateTimestamp();
        try {
            testRecorder.startRecording(scenario.getName(), timestamp);
        } catch (Exception e) {
            logger.error("Failed to start recording: ", e);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot(driver, scenario.getName(), timestamp);
        }
        try {
            testRecorder.stopRecording();
            String videoPath = "recordings/" + testRecorder.getVideoFilename();
            attachVideoToAllure(videoPath);
        } catch (Exception e) {
            logger.error("Failed to stop recording or attach video: ", e);
        } finally {
            cleanupState();
            ThreadLocalWebDriver.quitDriver();
        }
    }

    private void cleanupState() {
        logger.info("Cleaning up state after test");
        browserActions.deleteAllCookies();
        browserActions.navigateToUrl("about:blank");
    }

    private void attachVideoToAllure(String videoPath) {
        try (FileInputStream fis = new FileInputStream(videoPath)) {
            Allure.addAttachment("Test Video", "video/avi", fis, "avi");
        } catch (IOException e) {
            logger.error("Failed to attach video to Allure report: ", e);
        }
    }
}