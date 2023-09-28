package org.penz.emulator.cpu;

public final class BitUtil {

    private BitUtil() {
    }

    public static boolean getMSB(byte value) {
        return ((value & 0x80) >> 7) == 1;
    }

    public static boolean getLSB(byte value) {
        return (value & 0x01) == 1;
    }

    public static int setBit(int value, int bitPosition) {
        return (value | (1 << bitPosition));
    }
    public static int setBit(int value, int bitPosition, boolean bitValue) {
        if (bitValue) {
            return setBit(value, bitPosition);
        } else {
            return clearBit(value, bitPosition);
        }
    }

    public static int clearBit(int value, int bitPosition) {
        return (value & ~(1 << bitPosition));
    }

    public static int toggleBit(int value, int bitPosition) {
        return (value ^ (1 << bitPosition));
    }

    public static boolean getBit(int value, int bitPosition) {
        return ((value >> bitPosition) & 1) == 1;
    }

    public static short to16Bit(byte high, byte low) {
        return (short) ((high << 8) | low);
    }
}
