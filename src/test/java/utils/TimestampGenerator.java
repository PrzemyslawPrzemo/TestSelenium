package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampGenerator {
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyyMMdd_HHmmss")
    );

    public static String generateTimestamp() {
        return DATE_FORMAT.get().format(new Date());
    }
}
