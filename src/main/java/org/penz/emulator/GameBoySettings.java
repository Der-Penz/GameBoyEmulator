package org.penz.emulator;

import org.penz.emulator.graphics.enums.Palette;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class GameBoySettings {
    private final static String CONFIG_FILE = "gameboy_config.properties";
    private static GameBoySettings instance = null;
    private final Properties properties;

    private GameBoySettings() {
        properties = new Properties();
    }

    public static GameBoySettings getInstance() {
        if (instance == null) {
            instance = new GameBoySettings();
        }
        return instance;
    }

    public void saveSettings() {
        try {
            OutputStream output = new FileOutputStream(CONFIG_FILE);
            properties.store(output, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        try {
            properties.load(new FileInputStream(CONFIG_FILE));
            var p = Palette.values()[Integer.parseInt(properties.getProperty("palette"))];
            setPalette(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Palette getPalette() {
        return Palette.values()[getIntProperty("palette", Palette.GRAYSCALE.ordinal())];
    }

    public void setPalette(Palette palette) {
        storeIntProperty("palette", palette.ordinal());
    }

    public void setSize(int size) {
        storeIntProperty("size", size);
    }

    public int getSize() {
        return getIntProperty("size", 1);
    }

    private void storeIntProperty(String key, int value) {
        properties.setProperty(key, String.valueOf(value));
    }

    private int getIntProperty(String key, int defaultValue) {
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    }
}
