package org.penz.emulator.gui;

import org.penz.emulator.graphics.IDisplay;

import javax.swing.*;
import java.awt.*;

class ImageDisplay extends JPanel {

    protected final int imageWidth;
    protected final int imageHeight;
    private int[] pixelBuffer;
    protected int scale;

    public ImageDisplay(int width, int height, int scale) {
        imageWidth = width;
        imageHeight = height;
        pixelBuffer = new int[imageWidth * imageHeight];

        setScale(scale);
    }

    public void putPixel(int x, int y, int color) {
        int index = y * imageWidth + x;
        pixelBuffer[index] = color;
    }

    public void putPixel(int[] buffer) {
        if (buffer.length != pixelBuffer.length) {
            throw new IllegalArgumentException("Buffer size does not match display size");
        }
        pixelBuffer = buffer;
    }

    public void paintImage() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[] scaledBuffer = IDisplay.scaleBuffer(pixelBuffer, imageWidth, imageHeight, scale);
        IDisplay.drawBufferToGraphics(g, scaledBuffer, imageWidth * scale, imageHeight * scale);
    }

    public void setScale(int scale) {
        this.scale = scale;

        Dimension size = new Dimension(imageWidth * scale, imageHeight * scale);
        setPreferredSize(size);
    }
}