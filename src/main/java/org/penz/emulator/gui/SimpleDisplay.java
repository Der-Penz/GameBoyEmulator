package org.penz.emulator.gui;

import org.penz.emulator.GameBoy;
import org.penz.emulator.GameBoySettings;
import org.penz.emulator.graphics.IDisplay;
import org.penz.emulator.graphics.enums.Palette;
import org.penz.emulator.input.KeyboardController;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class SimpleDisplay extends JFrame implements IDisplay {

    private final KeyboardController controls;
    private final List<DebugFrame> debugFrames;
    AtomicBoolean isInFastMode = new AtomicBoolean(false);
    private ImageDisplay panel;
    private GameBoy gameBoy;
    private int framesCounter = 0;
    private KeyBoardControlViewer keyBoardControlViewer;

    public SimpleDisplay() {
        gameBoy = null;
        controls = new KeyboardController();
        debugFrames = new ArrayList<>();

        addKeyListener(controls);
        initializeUI();
    }

    /**
     * Reloads the gameboy with the given romPath
     *
     * @param romPath        the path to the rom file
     * @param runImmediately if true the gameboy will start running immediately
     */
    public void reloadGameBoy(String romPath, boolean runImmediately) {
        try {
            if (gameBoy != null) {
                gameBoy.pause();
            }
            debugFrames.forEach(DebugFrame::dispose);
            debugFrames.clear();

            if (keyBoardControlViewer != null) {
                keyBoardControlViewer.dispose();
            }
            gameBoy = new GameBoy(romPath, controls, this);
            panel.clearImage();
            if (!runImmediately) {
                gameBoy.pause();
                return;
            }
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    gameBoy.run(isInFastMode.get());
                    return null;
                }
            };

            worker.execute();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while loading the gameboy try restarting the program,\n further details: " + e.getMessage(), "Error while loading the gameboy", JOptionPane.ERROR_MESSAGE);
        }
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

        int DEFAULT_SCALE = 2;
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
                debugFrames.forEach(DebugFrame::updateFrame);
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

        JMenuItem bgMapViewer = new JMenuItem("BG Map Viewer");
        bgMapViewer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        bgMapViewer.addActionListener(e -> {
            registerDebugFrame(new BGMapViewer(gameBoy));
        });

        JMenuItem registerViewer = new JMenuItem("Register Viewer");
        registerViewer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        registerViewer.addActionListener(e -> {
            registerDebugFrame(new RegisterViewer(gameBoy.getCpu().getRegisters(), gameBoy.getMemory()));
        });


        JMenuItem tilesViewer = new JMenuItem("Tiles Viewer");
        tilesViewer.addActionListener(e -> {
            registerDebugFrame(new TilesViewer(gameBoy));
        });

        JMenuItem objectsViewer = new JMenuItem("Objects Viewer");
        objectsViewer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        objectsViewer.addActionListener(e -> {
            registerDebugFrame(new ObjectsViewer(gameBoy.getMemory()));
        });

        JMenuItem refresh = new JMenuItem("Refresh Viewer");
        refresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        refresh.addActionListener(e -> {
            debugFrames.forEach(DebugFrame::updateFrame);
        });

        debugMenu.add(refresh);
        debugMenu.add(bgMapViewer);
        debugMenu.add(objectsViewer);
        debugMenu.add(registerViewer);
        debugMenu.add(tilesViewer);

        return debugMenu;
    }

    private void registerDebugFrame(DebugFrame frame) {
        debugFrames.stream().filter(df -> df.getClass().equals(frame.getClass())).findFirst().ifPresent(df -> {
            df.dispose();
            debugFrames.remove(df);
        });
        debugFrames.add(frame);
        frame.transferFocus();
        requestFocus();
        frame.updateFrame();
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

        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(e -> {
            if (gameBoy != null) {
                gameBoy.pause();
                reloadGameBoy(gameBoy.getCartridge().getRomFile().getAbsolutePath(), true);
            }
        });

        JMenuItem loadRom = new JMenuItem("Load Rom");
        loadRom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        loadRom.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                reloadGameBoy(fileChooser
                        .getSelectedFile()
                        .getAbsolutePath(), true);
            }
        });

        JMenuItem loadRomPause = new JMenuItem("Load Rom and Pause");
        loadRomPause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
        loadRomPause.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                reloadGameBoy(fileChooser
                        .getSelectedFile()
                        .getAbsolutePath(), false);
            }
        });

        settingsMenu.add(loadRom);
        settingsMenu.add(loadRomPause);
        settingsMenu.add(reset);
        settingsMenu.add(keyBoardControl);
        settingsMenu.add(displayColor);
        settingsMenu.add(displaySize);
        return settingsMenu;
    }

    @Override
    public void putPixel(int x, int y, int color) {
        panel.putPixel(x, y, color);
    }

    long lastTimeStamp = System.currentTimeMillis();

    @Override
    public void onFrameReady() {
        framesCounter++;
        if (System.currentTimeMillis() - lastTimeStamp > 1000) {
            lastTimeStamp = System.currentTimeMillis();
            setTitle(gameBoy.getCartridge().getTitle() + ", FPS: " + framesCounter);
            framesCounter = 0;
        }

//        setTitle(gameBoy.getCartridge().getTitle() + ", Frame: " + ++framesCounter);
        panel.paintImage();
        debugFrames.forEach(DebugFrame::updateFrame);
    }

    @Override
    public void enableDisplay() {
        panel.paintImage();
    }

    @Override
    public void disableDisplay() {
        panel.clearImage();
    }
}
