package utils;

import io.cucumber.testng.PickleWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public class ScenarioRerunner {
    private static final Logger logger = LogManager.getLogger(ScenarioRerunner.class);
    private static final int MAX_RETRY_COUNT = Integer.parseInt(System.getProperty("maxRetryCount", "2"));
    private static final ThreadLocal<Integer> retryCount = new ThreadLocal<>();

    public static void runWithRetry(PickleWrapper pickleWrapper, Consumer<PickleWrapper> runMethod) {
        retryCount.set(0);
        boolean passed = false;

        while (!passed && retryCount.get() < MAX_RETRY_COUNT) {
            try {
                runMethod.accept(pickleWrapper);
                passed = true;
            } catch (Throwable t) {
                logger.warn("Test failed on attempt {}. Retrying...", retryCount.get() + 1);
                retryCount.set(retryCount.get() + 1);
                if (retryCount.get() >= MAX_RETRY_COUNT) {
                    logger.error("Test failed after {} attempts", MAX_RETRY_COUNT);
                    throw t;
                }
            }
        }

        if (passed && retryCount.get() > 0) {
            logger.info("Test passed on retry attempt {}", retryCount.get());
        }
    }

    public static void cleanUp() {
        retryCount.remove();
    }
}