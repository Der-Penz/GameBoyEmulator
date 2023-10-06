package org.penz.emulator.memory.cartridge;

public enum RAMSize {
    NONE(0x0, 0),
    KB_2(0x1, 1024 * 2),
    KB_8(0x2, 1024 * 8),
    KB_32(0x3, 1024 * 32),
    KB_128(0x4, 1024 * 128),
    KB_64(0x5, 1024 * 64);

    private final int id;
    private final int size;

    RAMSize(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public static RAMSize fromValue(int id) {
        for (RAMSize size : values()) {
            if (size.id == id) {
                return size;
            }
        }

        throw new IllegalArgumentException(String.format("Invalid RAM size value: 0x%02x", id));
    }

    public int getSize() {
        return size;
    }

    public int numberOfBanks() {
        final int RAM_BANK_SIZE = 8 * 1024;
        return size / RAM_BANK_SIZE;
    }
}
