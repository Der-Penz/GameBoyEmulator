package org.penz.emulator.graphics.enums;

public enum Palette {
    PALETTE_1(new int[]{0xFF, 0xC0, 0x60, 0x00}),
    PALETTE_2(new int[]{0xFF, 0xC0, 0x60, 0x00}),
    PALETTE_3(new int[]{0xFF, 0xC0, 0x60, 0x00});


    private int[] hexValues = new int[4];

    Palette(int[] hexValues) {
        this.hexValues = hexValues;
    }

    public int getColorById(int id) {
        return hexValues[id & 0x3];
    }
}
