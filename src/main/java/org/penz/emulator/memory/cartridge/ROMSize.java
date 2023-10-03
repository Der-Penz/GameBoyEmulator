package org.penz.emulator.memory.cartridge;

public enum ROMSize {
    _32KiB(32 * 1024),
    _64KiB(64 * 1024),
    _128KiB(128 * 1024),
    _256KiB(256 * 1024),
    _512KiB(512 * 1024),
    _1MiB(1024 * 1024),
    _2MiB(2048 * 1024),
    _4MiB(4096 * 1024),
    _8MiB(8192 * 1024),
    _1_1MiB(1152 * 1024),
    _1_2MiB(1280 * 1024),
    _1_5MiB(1536 * 1024);

    private final int expectedSize;

    ROMSize(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    public int numberOfBanks() {
        return expectedSize / (16 * 1024);
    }

    @Override
    public String toString() {
        return super.toString().substring(1);
    }

    public static ROMSize fromValue(int value) {
        switch (value) {
            case 0x00:
                return _32KiB;
            case 0x01:
                return _64KiB;
            case 0x02:
                return _128KiB;
            case 0x03:
                return _256KiB;
            case 0x04:
                return _512KiB;
            case 0x05:
                return _1MiB;
            case 0x06:
                return _2MiB;
            case 0x07:
                return _4MiB;
            case 0x08:
                return _8MiB;
            case 0x52:
                return _1_1MiB;
            case 0x53:
                return _1_2MiB;
            case 0x54:
                return _1_5MiB;
            default:
                throw new IllegalArgumentException("Invalid ROM size value: " + value);
        }
    }
}
