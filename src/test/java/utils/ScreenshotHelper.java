package utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ScreenshotHelper {

    private static final Logger logger = LogManager.getLogger(ScreenshotHelper.class);

    public static void takeScreenshot(WebDriver driver, String testName, String timestamp) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "screenshots/" + testName + "_" + timestamp + ".png";
        try {
            FileUtils.copyFile(srcFile, new File(filePath));
            logger.info("Screenshot saved: {}", filePath);
            Allure.addAttachment("Screenshot", new ByteArrayInputStream(FileUtils.readFileToByteArray(srcFile)));
        } catch (IOException e) {
            logger.error("Failed to save screenshot: ", e);
        }
    }
}
