package org.penz.emulator.memory.cartridge;

public enum ROMSize {
    _32KiB(0x0, 32 * 1024),
    _64KiB(0x1, 64 * 1024),
    _128KiB(0x2, 128 * 1024),
    _256KiB(0x3, 256 * 1024),
    _512KiB(0x4, 512 * 1024),
    _1MiB(0x5, 1024 * 1024),
    _2MiB(0x6, 2048 * 1024),
    _4MiB(0x7, 4096 * 1024),
    _8MiB(0x8, 8192 * 1024),
    _1_1MiB(0x52, 1152 * 1024),
    _1_2MiB(0x53, 1280 * 1024),
    _1_5MiB(0x54, 1536 * 1024);

    private final int id;

    private final int size;

    ROMSize(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public static ROMSize fromValue(int id) {
        for (ROMSize size : values()) {
            if (size.id == id) {
                return size;
            }
        }

        throw new IllegalArgumentException(String.format("Invalid ROM size value: 0x%02x", id));
    }

    public int getSize() {
        return size;
    }

    public int numberOfBanks() {
        int ROM_BANK_SIZE = 16 * 1024;
        return size / ROM_BANK_SIZE;
    }

}
