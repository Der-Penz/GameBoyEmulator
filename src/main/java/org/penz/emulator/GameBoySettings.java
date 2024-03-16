package org.penz.emulator;

import org.penz.emulator.graphics.enums.Palette;

public class GameBoySettings {

    private static GameBoySettings instance = null;

    private Palette palette = Palette.GRAYSCALE;

    private GameBoySettings() {
    }

    public static GameBoySettings getInstance() {
        if (instance == null) {
            instance = new GameBoySettings();
        }
        return instance;
    }

    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }
}
