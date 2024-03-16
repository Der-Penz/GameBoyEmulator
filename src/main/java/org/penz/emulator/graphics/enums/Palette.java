package org.penz.emulator.graphics.enums;

public enum Palette {
    GRAYSCALE(new int[]{0xFFFFFF, 0xAAAAAA, 0x555555, 0x000000}),
    GREEN(new int[]{0xE0F8D0, 0x88C070, 0x346856, 0x081820}),
    //make a blusih palette
    BLUE(new int[]{0x00FFFF, 0x00AAAA, 0x005555, 0x000000});

    private final int[] hexValues;

    Palette(int[] hexValues) {
        this.hexValues = hexValues;
    }

    public int getColorByIndex(int index) {
        return hexValues[index & 0x3];
    }
}
