package org.penz.emulator;

import org.penz.emulator.gui.SimpleDisplay;
import org.penz.emulator.input.KeyboardController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var romPath = "src/main/resources/roms/tetris.gb";

        var display = new SimpleDisplay();
        KeyboardController controls = new KeyboardController();
        display.addKeyListener(controls);
        var gameBoy = new GameBoy(romPath, controls, display);
        display.setGameBoy(gameBoy);
        gameBoy.run();
    }
}