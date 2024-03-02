package org.penz.emulator.cpu;

import org.penz.emulator.Constants;

public final class BitUtil {

    private BitUtil() {
    }

    /**
     * Get the most significant byte of a word
     *
     * @param value word
     * @return most significant byte
     */
    public static int getMSByte(int value) {
        checkIsWord(value);
        return (value & Constants.MSB_MASK) >> Constants.BYTE_SIZE;
    }

    /**
     * Get the least significant byte of a word
     *
     * @param value word
     * @return least significant byte
     */
    public static int getLSByte(int value) {
        checkIsWord(value);
        return (value & Constants.LSB_MASK);
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

    /**
     * Convert two bytes to a word
     *
     * @param high most significant byte
     * @param low  least significant byte
     * @return word
     */
    public static int toWord(int high, int low) {
        checkIsByte(high);
        checkIsByte(low);
        return shiftLeft(high, 1) | low;
    }

    public static int shiftLeft(int value, int numberOfBytes) {
        return value << (numberOfBytes * Constants.BYTE_SIZE);
    }

    public static int shiftRight(int value, int numberOfBytes) {
        return value >> (numberOfBytes * Constants.BYTE_SIZE);
    }

    /**
     * Check if value is a byte (0x00 - 0xFF)
     *
     * @param value to check
     * @throws IllegalArgumentException if value is not a byte (0x00 - 0xFF)
     */
    public static void checkIsByte(int value) {
        if (value > Constants.BYTE_MAX_VALUE || value < 0x00) {
            throw new IllegalArgumentException(String.format("Value %d is not a byte (0x00 - 0xFF)", value));
        }
    }

    /**
     * Check if value is a word (0x0000 - 0xFFFF)
     *
     * @param value to check
     * @throws IllegalArgumentException if value is not a word (0x0000 - 0xFFFF)
     */
    public static void checkIsWord(int value) {
        if (value > Constants.WORD_MAX_VALUE || value < 0x0000) {
            throw new IllegalArgumentException(String.format("Value %d is not a word (0x0000 - 0xFFFF)", value));
        }
    }

    /**
     * Convert a unsigned byte to a signed byte
     *
     * @param value unsigned byte
     * @return signed byte
     */
    public static int toSignedByte(int value) {
        if (getBit(value, 7)) {
            return value - 0x100;
        } else {
            return value;
        }
    }

    public static int toByte(int value) {
        return value & Constants.BYTE_MAX_VALUE;
    }

    public static String toHex(int value) {
        return Integer.toHexString(value);
    }

    public static boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
