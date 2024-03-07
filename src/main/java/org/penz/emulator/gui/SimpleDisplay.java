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

    private RegisterViewer registerViewer;

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
        JMenu settingsMenu = new JMenu("Settings");

        JMenuItem tick = new JMenuItem("Tick");
        tick.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        tick.addActionListener(e -> {
            if (gameBoy != null && !gameBoy.isRunning()) {
                gameBoy.tick();
                if (registerViewer != null) {
                    registerViewer.updateRegisterData();
                }
            }
        });
        JMenuItem run = new JMenuItem("Start");
        run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        run.addActionListener(e -> {
            if (gameBoy != null && !gameBoy.isRunning()) {
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        gameBoy.run(false);
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
        JMenuItem registerViewer = new JMenuItem("Register Viewer");
        registerViewer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        registerViewer.addActionListener(e -> {
            RegisterViewer r = new RegisterViewer(gameBoy.getCpu().getRegisters(), gameBoy.getMemory());
            r.setLocation(getX() + getWidth(), getY());
            this.registerViewer = r;
        });

        JMenu displaySize = new JMenu("Size");

        JRadioButtonMenuItem x1 = new JRadioButtonMenuItem("1x1");
        x1.addActionListener(e -> {
            panel.setScale(1);
            pack();
        });
        JRadioButtonMenuItem x2 = new JRadioButtonMenuItem("2x2");
        x2.addActionListener(e -> {
            panel.setScale(2);
            pack();
        });
        JRadioButtonMenuItem x3 = new JRadioButtonMenuItem("3x3");
        x3.addActionListener(e -> {
            panel.setScale(3);
            pack();
        });

        ButtonGroup group = new ButtonGroup();
        group.add(x1);
        group.add(x2);
        group.add(x3);
        displaySize.add(x1);
        displaySize.add(x2);
        displaySize.add(x3);

        settingsMenu.add(displaySize);

        debugMenu.add(tileDataViewer);
        debugMenu.add(registerViewer);

        menuBar.add(settingsMenu);
        menuBar.add(controlMenu);
        menuBar.add(debugMenu);
        setJMenuBar(menuBar);

        panel = new ImageDisplay(160, 144, 1);
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
        if (registerViewer != null) {
            registerViewer.updateRegisterData();
        }
    }

    @Override
    public void enableDisplay() {
        panel.setVisible(true);
    }

    @Override
    public void disableDisplay() {
        panel.setVisible(true);
    }
}
