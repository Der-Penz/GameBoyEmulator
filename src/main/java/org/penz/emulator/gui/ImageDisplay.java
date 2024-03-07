package org.penz.emulator.gui;

import org.penz.emulator.graphics.IDisplay;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

class ImageDisplay extends JPanel {

    private static final int BORDER_SIZE = 10;
    private final int[] pixelBuffer;

    private final int imageWidth;
    private final int imageHeight;

    private int scale;

    public ImageDisplay(int width, int height, int scale) {
        imageWidth = width;
        imageHeight = height;
        pixelBuffer = new int[imageWidth * imageHeight];


        setScale(scale);
        setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
    }

    public void putPixel(int x, int y, int color) {
        int index = y * imageWidth + x;
        pixelBuffer[index] = color;
    }

    public void paintImage() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage image = new BufferedImage(imageWidth * scale, imageHeight * scale, BufferedImage.TYPE_INT_ARGB);

        int[] colorData = new int[pixelBuffer.length];
        for (int i = 0; i < pixelBuffer.length; i++) {
            int color = pixelBuffer[i];
            colorData[i] = 0xFF000000 | color;
        }

        int[] scaledBuffer = IDisplay.scaleBuffer(colorData, imageWidth, imageHeight, scale);

        image.setRGB(0, 0, imageWidth * scale, imageHeight * scale, scaledBuffer, 0, imageWidth * scale);

        g.drawImage(image, BORDER_SIZE, BORDER_SIZE, this);
    }

    public void setScale(int scale) {
        this.scale = scale;
        Dimension size = new Dimension(imageWidth * scale + BORDER_SIZE * 2, imageWidth * scale + BORDER_SIZE * 2);
        setPreferredSize(size);
    }
}