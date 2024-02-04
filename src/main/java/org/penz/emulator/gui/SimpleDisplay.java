package org.penz.emulator.gui;

import org.penz.emulator.GameBoy;
import org.penz.emulator.graphics.IDisplay;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class SimpleDisplay extends JFrame implements IDisplay {

    private ImageDisplay panel;

    private GameBoy gameBoy;
    private int framesCounter = 0;

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

        JMenuBar menuBar = new JMenuBar();
        JMenu controlMenu = new JMenu("Control");
        controlMenu.setMnemonic(KeyEvent.VK_C);
        JMenu debugMenu = new JMenu("Debug");
        debugMenu.setMnemonic(KeyEvent.VK_D);

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

        controlMenu.add(tick);
        controlMenu.add(run);
        controlMenu.add(frame);
        controlMenu.add(pause);

        JMenuItem tileDataViewer = new JMenuItem("Tile Data Viewer");
        tileDataViewer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        tileDataViewer.addActionListener(e -> {
            gameBoy.pause();
            TileDataViewer t = new TileDataViewer(gameBoy.getMemory(), framesCounter);
            t.setLocation(getX() + getWidth(), getY());
            t.updateTileData();
        });

        debugMenu.add(tileDataViewer);

        menuBar.add(controlMenu);
        menuBar.add(debugMenu);
        setJMenuBar(menuBar);

        panel = new ImageDisplay(160, 144);
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
        framesCounter++;
        setTitle("Color Array Display, Frame: " + framesCounter);
        panel.paintImage();
    }

}
