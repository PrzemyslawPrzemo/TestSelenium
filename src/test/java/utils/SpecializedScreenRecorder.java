package utils;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SpecializedScreenRecorder extends ScreenRecorder {
    private final String name;

    public SpecializedScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea,
                                     Format fileFormat, Format screenFormat, Format mouseFormat,
                                     File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, null, movieFolder);
        this.name = name;
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            if (!movieFolder.mkdirs()) {
                throw new IOException("Failed to create movie folder: " + movieFolder);
            }
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }

        return new File(movieFolder, name + "." + Registry.getInstance().getExtension(fileFormat));
    }
}
