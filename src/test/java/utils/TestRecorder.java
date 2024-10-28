package utils;

import org.monte.media.Format;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class TestRecorder {
    private SpecializedScreenRecorder screenRecorder;
    private String videoFilename;

    public void startRecording(String testName, String timestamp) throws IOException, AWTException {
        File file = new File("recordings/");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle captureSize = new Rectangle(0, 0, screenSize.width, screenSize.height);

        videoFilename = testName + "_" + timestamp;

        this.screenRecorder = new SpecializedScreenRecorder(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration(),
                captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, "video/avi"),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(60),
                        QualityKey, 1.0f,
                        KeyFrameIntervalKey, 30),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                        FrameRateKey, Rational.valueOf(30)),
                file, videoFilename);
        this.screenRecorder.start();
    }

    public void stopRecording() throws IOException {
        if (this.screenRecorder != null) {
            this.screenRecorder.stop();
        }
    }

    public String getVideoFilename() {
        return videoFilename + ".avi";
    }
}
