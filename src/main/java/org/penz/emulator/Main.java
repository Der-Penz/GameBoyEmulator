package org.penz.emulator;

import org.penz.emulator.gui.SimpleDisplay;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        var romPath = args.length > 0 ? args[0] : "src/main/resources/roms/tetris.gb";

        GameBoySettings.getInstance().loadSettings();

        SimpleDisplay display = new SimpleDisplay();
        display.reloadGameBoy(romPath, true);
    }
}