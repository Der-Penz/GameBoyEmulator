package org.penz.emulator.graphics.enums;

public enum TileDataArea {

    AREA_1(0x8800, 0x97FF),
    AREA_2(0x8000, 0x8FFF);

    private final int startAddress;
    private final int endAddress;

    TileDataArea(int startAddress, int endAddress) {
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public static TileDataArea fromValue(int value) {
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
