package org.penz.emulator;

import org.penz.emulator.gui.SimpleDisplay;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        var romPath = args.length > 0 ? args[0] : "src/main/resources/roms/tetris.gb";

        var display = new SimpleDisplay();
        var gameBoy = new GameBoy(romPath, display.getControls(), display);
        display.setGameBoy(gameBoy);
    }
}