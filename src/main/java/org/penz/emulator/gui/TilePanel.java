package org.penz.emulator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class TilePanel extends JPanel {

    private final int imageWidth = 8;
    private Integer[] pixelBuffer;

    public TilePanel() {
        setSize(imageWidth, imageWidth);
    }

    public void setPixel(Integer[] colors) {
        pixelBuffer = colors;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage image = new BufferedImage(imageWidth, imageWidth, BufferedImage.TYPE_INT_ARGB);

        int[] colorData = new int[pixelBuffer.length];
        for (int i = 0; i < pixelBuffer.length; i++) {
            int x = i % imageWidth;
            int y = i / imageWidth - 1;
            int color = pixelBuffer[i];
            colorData[i] = 0xFF000000 | color;
        }

        image.setRGB(0, 0, imageWidth, imageWidth, colorData, 0, imageWidth);

        g.drawImage(image, 0, 0, this);
    }
}
