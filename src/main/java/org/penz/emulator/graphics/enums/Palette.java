package org.penz.emulator.graphics.enums;

public enum Palette {
    GRAYSCALE(new int[]{0xFFFFFF, 0xAAAAAA, 0x555555, 0x000000}),
    PALETTE_2(new int[]{0xFF, 0xC0, 0x60, 0x00}),
    PALETTE_3(new int[]{0xFF, 0xC0, 0x60, 0x00});


    private final int[] hexValues;

    Palette(int[] hexValues) {
        this.hexValues = hexValues;
    }

    public int getColorByIndex(int index) {
        return hexValues[index & 0x3];
    }
}
