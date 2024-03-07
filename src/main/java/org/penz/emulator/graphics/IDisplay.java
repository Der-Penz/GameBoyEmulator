package org.penz.emulator.graphics;

public interface IDisplay {

    static int[] scaleBuffer(int[] pixelBuffer, int width, int height, int scale) {
        int[] scaledBuffer = new int[width * height * scale * scale];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = pixelBuffer[y * width + x];
                for (int dy = 0; dy < scale; dy++) {
                    for (int dx = 0; dx < scale; dx++) {
                        scaledBuffer[(y * scale + dy) * width * scale + x * scale + dx] = color;
                    }
                }
            }
        }
        return scaledBuffer;
    }

    void putPixel(int x, int y, int color);

    void enableDisplay();

    void disableDisplay();

    void onFrameReady();
}
