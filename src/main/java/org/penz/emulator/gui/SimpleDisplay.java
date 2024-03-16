package org.penz.emulator.gui;

import org.penz.emulator.GameBoy;
import org.penz.emulator.GameBoySettings;
import org.penz.emulator.graphics.IDisplay;
import org.penz.emulator.graphics.enums.Palette;
import org.penz.emulator.input.KeyboardController;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;


public class SimpleDisplay extends JFrame implements IDisplay {

    AtomicBoolean isInFastMode = new AtomicBoolean(false);
    private ImageDisplay panel;
    private GameBoy gameBoy;
    private final KeyboardController controls;
    private int framesCounter = 0;
    private RegisterViewer registerViewer;
    private BGMapViewer tileDataViewer;
    private TilesViewer tilesViewer;
    private KeyBoardControlViewer keyBoardControlViewer;

    public SimpleDisplay() {
        gameBoy = null;
        controls = new KeyboardController();
        addKeyListener(controls);
        initializeUI();
    }

    public void setGameBoy(GameBoy gameBoy) {
        this.gameBoy = gameBoy;
        gameBoy.run(isInFastMode.get());
    }

    private void initializeUI() {
        setTitle("Gameboy Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(getSettingsMenu());
        menuBar.add(getControlsMenu());
        menuBar.add(getDebugMenu());
        setJMenuBar(menuBar);

        int DEFAULT_SCALE = 1;
        panel = new ImageDisplay(GameBoy.SCREEN_WIDTH, GameBoy.SCREEN_HEIGHT, DEFAULT_SCALE);
        add(panel);

        pack();
        setLocationRelativeTo(null);
        requestFocus();
    }

    private JMenu getControlsMenu() {
        JMenu controlMenu = new JMenu("Control");
        controlMenu.setMnemonic(KeyEvent.VK_C);

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
                        gameBoy.run(isInFastMode.get());
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

        JCheckBoxMenuItem fastMode = new JCheckBoxMenuItem("Fast Mode");
        fastMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.SHIFT_DOWN_MASK));
        fastMode.addActionListener(e -> {
            if (gameBoy == null) {
                return;
            }
            isInFastMode.set(fastMode.getState());
            if (gameBoy.isRunning()) {
                gameBoy.pause();
                run.doClick();
            }
        });
        fastMode.setState(isInFastMode.get());

        controlMenu.add(fastMode);
        controlMenu.add(run);
        controlMenu.add(pause);
        controlMenu.add(tick);
        controlMenu.add(frame);
        return controlMenu;
    }

    private JMenu getDebugMenu() {
        JMenu debugMenu = new JMenu("Debug");
        debugMenu.setMnemonic(KeyEvent.VK_D);

        JMenuItem tileDataViewer = new JMenuItem("Tile Data Viewer");
        tileDataViewer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        tileDataViewer.addActionListener(e -> {
            if (this.tileDataViewer != null) {
                this.tileDataViewer.dispose();
            }
            BGMapViewer t = new BGMapViewer(gameBoy);
            t.setLocation(getX() + getWidth(), getY() - (t.getHeight() / 2));
            t.updateTileData();
            this.tileDataViewer = t;
        });
        JMenuItem registerViewer = new JMenuItem("Register Viewer");
        registerViewer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        registerViewer.addActionListener(e -> {
            if (this.registerViewer != null) {
                this.registerViewer.dispose();
            }
            RegisterViewer r = new RegisterViewer(gameBoy.getCpu().getRegisters(), gameBoy.getMemory());
            r.setLocation(getX() + getWidth(), getY());
            this.registerViewer = r;
        });
        JMenuItem tilesViewer = new JMenuItem("Tiles Viewer");
        tilesViewer.addActionListener(e -> {
            if (this.tilesViewer != null) {
                this.tilesViewer.dispose();
            }
            gameBoy.pause();
            TilesViewer t = new TilesViewer(gameBoy);
            t.setLocation(getX() + getWidth(), getY() - (t.getHeight() / 2));
            t.updateTileData();
            this.tilesViewer = t;
        });

        debugMenu.add(tileDataViewer);
        debugMenu.add(registerViewer);
        debugMenu.add(tilesViewer);

        return debugMenu;
    }

    private JMenu getSettingsMenu() {
        JMenu settingsMenu = new JMenu("Settings");

        JMenu displaySize = new JMenu("Size");
        ButtonGroup sizeGroup = new ButtonGroup();

        for (int i = 0; i < 7; i++) {
            int scale = i + 1;
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(scale + "x" + scale);
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0 + scale, InputEvent.SHIFT_DOWN_MASK));
            item.addActionListener(e -> {
                panel.setScale(scale);
                pack();
                setLocationRelativeTo(null);
            });
            sizeGroup.add(item);
            displaySize.add(item);
        }

        JMenu displayColor = new JMenu("Color palette");
        ButtonGroup colorGroup = new ButtonGroup();

        for (Palette palette : Palette.values()) {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(palette.name());
            item.addActionListener(e -> {
                GameBoySettings.getInstance().setPalette(palette);
            });
            colorGroup.add(item);
            displayColor.add(item);
        }


        JMenuItem keyBoardControl = new JMenuItem("KeyBoard Bindings");
        keyBoardControl.addActionListener(e -> {
            if (this.keyBoardControlViewer != null) {
                this.keyBoardControlViewer.dispose();
            }
            gameBoy.pause();
            KeyBoardControlViewer k = new KeyBoardControlViewer(controls);
            k.setLocation(getX() + getWidth(), getY());
            this.keyBoardControlViewer = k;
        });


        settingsMenu.add(keyBoardControl);
        settingsMenu.add(displayColor);
        settingsMenu.add(displaySize);
        return settingsMenu;
    }

    @Override
    public void putPixel(int x, int y, int color) {
        panel.putPixel(x, y, color);
    }

    @Override
    public void onFrameReady() {
        framesCounter++;
        setTitle(gameBoy.getCartridge().getTitle() + ", Frame: " + framesCounter);
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

    public KeyboardController getControls() {
        return controls;
    }
}
