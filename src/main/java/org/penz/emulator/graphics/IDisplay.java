package org.penz.emulator.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IDisplay {

    /**
     * Scales a pixel buffer by a given scale
     *
     * @param pixelBuffer the pixel buffer to scale
     * @param width       the width of the pixel buffer
     * @param height      the height of the pixel buffer
     * @param scale       the scale to apply
     * @return the scaled pixel buffer
     */
    static int[] scaleBuffer(int[] pixelBuffer, int width, int height, int scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("Scale factor must be greater than 0");
        }

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

    /**
     * Draw the buffer to the graphics object
     *
     * @param g      the graphics object
     * @param buffer the buffer to draw
     * @param width  the width of the resulting image
     * @param height the height of the resulting image
     */
    static void drawBufferToGraphics(Graphics g, int[] buffer, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, buffer, 0, width);
        g.drawImage(image, 0, 0, null);
    }

    void putPixel(int x, int y, int color);

    void enableDisplay();

    void disableDisplay();

    void onFrameReady();
}
