package org.penz.emulator;

import org.penz.emulator.graphics.enums.Palette;
import org.penz.emulator.input.Button;

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

    public int getSize() {
        return getIntProperty("size", 1);
    }

    public void setSize(int size) {
        storeIntProperty("size", size);
    }

    public void setButton(Button button, int keyCode) {
        storeIntProperty("button:" + button.name(), keyCode);
    }

    public int getButton(Button button) {
        return getIntProperty("button:" + button.name(), -1);
    }

    public String[][] getRecentRoms() {
        String recentRomsPath = properties.getProperty("recent_roms_path", "");
        String recentRomsName = properties.getProperty("recent_roms_name", "");

        if (recentRomsPath.isEmpty() || recentRomsName.isEmpty()) {
            return new String[0][0];
        }

        String[] paths = recentRomsPath.split("§§");
        String[] names = recentRomsName.split("§§");


        String[][] recentRoms = new String[paths.length][2];
        for (int i = 0; i < paths.length; i++) {
            recentRoms[i][0] = paths[i];
            recentRoms[i][1] = names[i];
        }

        return recentRoms;
    }

    public void setRecentRoms(String path, String name) {
        //TODO: Check if max of 5 recent roms is working
        try {
            String recentRomsPath = properties.getProperty("recent_roms_path", "");
            String recentRomsName = properties.getProperty("recent_roms_name", "");

            String[] paths = recentRomsPath.split("§§");
            String[] names = recentRomsName.split("§§");

            StringBuilder newPaths = new StringBuilder();
            StringBuilder newNames = new StringBuilder();

            newPaths.append(path).append("§§");
            newNames.append(name).append("§§");
            for (int i = 0; i < 4; i++) {
                if (paths.length > i && !paths[i].isEmpty() && !names[i].isEmpty()) {
                    if (paths[i].equals(path) || names[i].equals(name)) {
                        continue;
                    }
                    newPaths.append(paths[i]);
                    newNames.append(names[i]);
                } else {
                    break;
                }
                newPaths.append("§§");
                newNames.append("§§");
            }

            properties.setProperty("recent_roms_path", newPaths.toString());
            properties.setProperty("recent_roms_name", newNames.toString());
        } catch (Exception e) {
            properties.setProperty("recent_roms_path", "");
            properties.setProperty("recent_roms_name", "");
        }

    }

    private void storeIntProperty(String key, int value) {
        properties.setProperty(key, String.valueOf(value));
    }

    private int getIntProperty(String key, int defaultValue) {
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    }
}
