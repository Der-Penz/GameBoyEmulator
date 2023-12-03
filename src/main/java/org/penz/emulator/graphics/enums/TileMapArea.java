package org.penz.emulator.graphics.enums;

public enum TileMapArea {
    AREA_1(0x9800, 0x9BFF),
    AREA_2(0x9C00, 0x9FFF);

    private final int startAddress;
    private final int endAddress;

    TileMapArea(int startAddress, int endAddress) {
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public static TileMapArea fromValue(int value) {
        if (value == 0) {
            return AREA_1;
        } else {
            return AREA_2;
        }
    }

    public boolean contains(int address) {
        return address >= startAddress && address <= endAddress;
    }

    public int getStartAddress() {
        return startAddress;
    }

    public int getEndAddress() {
        return endAddress;
    }
}
