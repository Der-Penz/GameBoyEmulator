package org.penz.emulator.gui;

import org.penz.emulator.GameBoy;
import org.penz.emulator.graphics.IDisplay;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public class SimpleDisplay extends JFrame implements IDisplay {

    private ColorArrayPanel panel;

    private GameBoy gameBoy;
    private int frames = 0;

    public SimpleDisplay() {
        gameBoy = null;
        initializeUI();
    }

    public void setGameBoy(GameBoy gameBoy) {
        this.gameBoy = gameBoy;
    }

    private void initializeUI() {
        setTitle("Gameboy Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(195, 200);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Control");
        menu.setMnemonic(KeyEvent.VK_C);

        JMenuItem tick = new JMenuItem("Tick");
        tick.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        tick.addActionListener(e -> {
            if (gameBoy != null && !gameBoy.isRunning()) {
                gameBoy.tick();
            }
        });
        JMenuItem run = new JMenuItem("Start");
        run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        run.addActionListener(e -> {
            if (gameBoy != null && !gameBoy.isRunning()) {
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        gameBoy.run();
                        return null;
                    }
                };

                worker.execute();
            }
        });
        JMenuItem pause = new JMenuItem("Pause");
        pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        pause.addActionListener(e -> {
            if (gameBoy != null && gameBoy.isRunning()) {
                gameBoy.pause();
            }
        });

        JMenuItem frame = new JMenuItem("Frame");
        frame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        frame.addActionListener(e -> {
            if (gameBoy != null && !gameBoy.isRunning()) {
                gameBoy.frame();
            }
        });

        menu.add(tick);
        menu.add(run);
        menu.add(frame);
        menu.add(pause);
        menuBar.add(menu);
        setJMenuBar(menuBar);

//        setLayout(new Bo);

        panel = new ColorArrayPanel(160, 144);
        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
        pack();
    }

    @Override
    public void putPixel(int x, int y, int color) {
        panel.putPixel(x, y, color);
    }

    @Override
    public void onFrameReady() {
        System.out.println("Frame ready");
        frames++;
        setTitle("Color Array Display - " + frames);
        panel.paintImage();
        requestFocus();
    }

    private static class ColorArrayPanel extends JPanel {
        private final int[] pixelBuffer;

        private final int imageWidth;
        private final int imageHeight;

        public ColorArrayPanel(int width, int height) {
            imageWidth = width;
            imageHeight = height;
            pixelBuffer = new int[imageWidth * imageHeight];

            int border = 10;
            Dimension size = new Dimension(width + border * 2, height + border * 2);
            setSize(size);
            setMinimumSize(size);
            setPreferredSize(size);
            setBorder(new EmptyBorder(border, border, border, border));
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
                int y = i / imageWidth - 1;
                int color = pixelBuffer[i];
                colorData[i] = 0xFF000000 | color;
            }

            image.setRGB(0, 0, imageWidth, imageHeight, colorData, 0, imageWidth);

            g.drawImage(image, 10, 10, this);
        }
    }
}
