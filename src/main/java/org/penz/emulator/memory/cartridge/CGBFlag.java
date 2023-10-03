package org.penz.emulator.memory.cartridge;

public enum CGBFlag {
    CGB_ONLY(0xC0), CGB_SUPPORTED(0x80);

    private final int value;

    CGBFlag(int flag) {
        this.value = flag;
    }

    public int getValue() {
        return value;
    }

    public static CGBFlag fromValue(int value) {
        for (CGBFlag flag : CGBFlag.values()) {
            if (flag.getValue() == value) {
                return flag;
            }
        }
        return CGB_SUPPORTED;
    }

}
