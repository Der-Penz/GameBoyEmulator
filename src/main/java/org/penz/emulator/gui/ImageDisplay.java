package org.penz.emulator.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

class ImageDisplay extends JPanel {

    private static final int BORDER_SIZE = 10;
    private final int[] pixelBuffer;

    private final int imageWidth;
    private final int imageHeight;

    public ImageDisplay(int width, int height) {
        imageWidth = width;
        imageHeight = height;
        pixelBuffer = new int[imageWidth * imageHeight];


        Dimension size = new Dimension(width + BORDER_SIZE * 2, height + BORDER_SIZE * 2);
        setSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
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

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        int[] colorData = new int[pixelBuffer.length];
        for (int i = 0; i < pixelBuffer.length; i++) {
            int x = i % imageWidth;
            int y = i / imageWidth;
            int color = pixelBuffer[i];
            colorData[i] = 0xFF000000 | color;
        }

        image.setRGB(0, 0, imageWidth, imageHeight, colorData, 0, imageWidth);

        g.drawImage(image, BORDER_SIZE, BORDER_SIZE, this);
    }
}