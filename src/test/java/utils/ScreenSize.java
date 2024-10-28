package utils;

public enum ScreenSize {
    DESKTOP(1920, 1080, 4, 1900, 20000, 24, false),
    TABLET(1650, 800, 4, 1600, 20000, 24, false),
    MOBILE(1400, 800, 4, 1280, 1600, 20, false);

    private final int width;
    private final int height;
    private final int unit;
    private final int minWidth;
    private final int maxWidth;
    private final int margin;
    private final boolean isMobile;

    ScreenSize(int width, int height, int unit, int minWidth, int maxWidth, int margin, boolean isMobile) {
        this.width = width;
        this.height = height;
        this.unit = unit;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.margin = margin;
        this.isMobile = isMobile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getUnit() {
        return unit;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMargin() {
        return margin;
    }

    public boolean isMobile() {
        return isMobile;
    }
}

