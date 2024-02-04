package org.penz.emulator.graphics.enums;

public enum TileDataArea {

    //Area 1: 0x8800-0x97FF signed pointer
    AREA_1(0x9000),
    //Area 2: 0x8000-0x8FFF unsigned pointer
    AREA_2(0x8000);

    private final int baseAddress;

    TileDataArea(int baseAddress) {
        this.baseAddress = baseAddress;
    }

    public static TileDataArea fromValue(int value) {
        if (value == 0) {
            return AREA_1;
        } else {
            return AREA_2;
        }
    }

    public int getBaseAddress() {
        return baseAddress;
    }

}
