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

    private BGMapViewer tileDataViewer;

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
            BGMapViewer t = new BGMapViewer(gameBoy);
            t.setLocation(getX() + getWidth(), getY() - (t.getHeight() / 2));
            t.updateTileData();
            this.tileDataViewer = t;
        });
        JMenuItem registerViewer = new JMenuItem("Register Viewer");
        registerViewer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        registerViewer.addActionListener(e -> {
            RegisterViewer r = new RegisterViewer(gameBoy.getCpu().getRegisters(), gameBoy.getMemory());
            r.setLocation(getX() + getWidth(), getY());
            this.registerViewer = r;
        });

        JMenu settingsMenu = getSettingsMenu();


        debugMenu.add(tileDataViewer);
        debugMenu.add(registerViewer);

        menuBar.add(settingsMenu);
        menuBar.add(controlMenu);
        menuBar.add(debugMenu);
        setJMenuBar(menuBar);

        panel = new ImageDisplay(GameBoy.SCREEN_WIDTH, GameBoy.SCREEN_HEIGHT, 1);
        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
        pack();
    }

    private JMenu getSettingsMenu() {
        JMenu settingsMenu = new JMenu("Settings");

        JMenu displaySize = new JMenu("Size");
        ButtonGroup group = new ButtonGroup();

        for (int i = 0; i < 7; i++) {
            int scale = i + 1;
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(scale + "x" + scale);
            item.addActionListener(e -> {
                panel.setScale(scale);
                pack();
            });
            group.add(item);
            displaySize.add(item);
        }

        settingsMenu.add(displaySize);
        return displaySize;
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
        if (tileDataViewer != null) {
            tileDataViewer.updateTileData();
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
