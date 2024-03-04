package org.penz.emulator.graphics.enums;

public enum PpuMode {
    PIXEL_TRANSFER(0b11),
    OAM_SCAN(0b10),
    H_BLANK(0b00),
    V_BLANK(0b01);

    private final int bitRepresentation;

    PpuMode(int bitRepresentation) {
        this.bitRepresentation = bitRepresentation;
    }

    public int getBitRepresentation() {
        return bitRepresentation;
    }

}
