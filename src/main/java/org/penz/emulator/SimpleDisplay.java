package org.penz.emulator;

import org.penz.emulator.graphics.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class SimpleDisplay extends JFrame implements Display {

    private ColorArrayPanel panel;

    public SimpleDisplay() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Color Array Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(160, 144);

        panel = new ColorArrayPanel();
        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void putPixel(int x, int y, int color) {
        panel.putPixel(x, y, color);
    }

    @Override
    public void onFrameReady() {
        System.out.println("Frame ready");
        panel.paintImage();
    }

    private class ColorArrayPanel extends JPanel {
        private List<Integer> pixelColors;
        private int width = 160;
        private int height = 144;

        public ColorArrayPanel() {
            pixelColors = new ArrayList<>(width * height);
            for (int i = 0; i < width * height; i++) {
                pixelColors.add(Color.BLACK.getRGB());
            }
        }

        public void putPixel(int x, int y, int color) {
            int index = y * width + x;
            pixelColors.set(index, color);
        }

        public void paintImage() {
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            int[] colorData = new int[width * height];
            for (int i = 0; i < pixelColors.size(); i++) {
                int x = i % width;
                int y = i / width;
                int color = pixelColors.get(i);
                colorData[i] = 0xFF000000 | color;
            }

            image.setRGB(0, 0, width, height, colorData, 0, width);

            g.drawImage(image, 0, 0, this);
        }
    }
}
