package org.penz.emulator.gui;

import org.penz.emulator.graphics.IDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class TilePanel extends JPanel {

    private final int imageWidth = 8;

    private int scale;
    private Integer[] pixelBuffer;

    public TilePanel(int scale) {
        this.scale = scale;
        setPreferredSize(new Dimension(imageWidth * scale, imageWidth * scale));
    }

    public void setPixel(Integer[] colors) {
        pixelBuffer = colors;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage image = new BufferedImage(imageWidth * scale, imageWidth * scale, BufferedImage.TYPE_INT_RGB);

        int[] colorData = new int[pixelBuffer.length];
        for (int i = 0; i < pixelBuffer.length; i++) {
            int x = i % imageWidth;
            int y = i / imageWidth - 1;
            int color = pixelBuffer[i];
            colorData[i] = 0xFF000000 | color;
        }

        int[] scaledColorData = IDisplay.scaleBuffer(colorData, imageWidth, imageWidth, scale);

        image.setRGB(0, 0, imageWidth * scale, imageWidth * scale, scaledColorData, 0, imageWidth * scale);

        g.drawImage(image, 0, 0, this);
    }
}
