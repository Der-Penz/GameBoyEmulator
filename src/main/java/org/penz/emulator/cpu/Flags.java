package org.penz.emulator.cpu;

public class Flags {

    private static final int Z_BYTE_POS = 7;
    private static final int N_BYTE_POS = 6;
    private static final int H_BYTE_POS = 5;
    private static final int C_BYTE_POS = 4;

    /**
     * 8 Bit flags register
     * Contains information about the result of the most recent instruction that has affected flags.
     * Holds the following flags in this order:
     * ZNHC 0000
     * Z - Zero Flag
     * N - Subtract Flag
     * H - Half Carry Flag
     * C - Carry Flag
     */
    private byte flags;

    public Flags() {
        this.flags = 0x00;
    }

    /**
     * Sets the value of the Z flag
     * @param bitValue true if bit should be set, false if bit should be cleared
     */
    public void setZ(boolean bitValue) {
        this.flags = (byte) BitUtil.setBit(flags, Z_BYTE_POS, bitValue);
    }

    /**
     * Sets the value of the N flag
     * @param bitValue true if bit should be set, false if bit should be cleared
     */
    public void setN(boolean bitValue) {
        this.flags = (byte) BitUtil.setBit(flags, N_BYTE_POS, bitValue);
    }

    /**
     * Sets the value of the H flag
     * @param bitValue true if bit should be set, false if bit should be cleared
     */
    public void setH(boolean bitValue) {
        this.flags = (byte) BitUtil.setBit(flags, H_BYTE_POS, bitValue);
    }

    /**
     * Sets the value of the C flag
     * @param bitValue true if bit should be set, false if bit should be cleared
     */
    public void setC(boolean bitValue) {
        this.flags = (byte) BitUtil.setBit(flags, C_BYTE_POS, bitValue);
    }

    /**
     * This bit is set if and only if the result of an operation is zero. Used by conditional jumps.
     * @return the value of the Z flag
     */
    public boolean isZ() {
        return BitUtil.getBit(flags, Z_BYTE_POS);
    }

    /**
     * Carry bit, set by following cases:
     * - result of 8 bit addition is greater than 0xFF
     * - result of 16 bit addition is greater than 0xFFFF
     * - when rotating or shifting, the bit that falls off is set to 1
     * - when the result of a subtraction or comparison is lower than zero
     * @return the value of the C flag
     */
    public boolean isC() {
        return BitUtil.getBit(flags, C_BYTE_POS);
    }

    /**
     * This bit is set if a subtraction was performed in the last math instruction. Used by conditional jumps.
     * @return the value of the N flag
     */
    public boolean isN() {
        return BitUtil.getBit(flags, N_BYTE_POS);
    }

    /**
     * Carry for the lower 4 bits of the result
     * @return the value of the H flag
     */
    public boolean isH() {
        return BitUtil.getBit(flags, H_BYTE_POS);
    }

    /**
     * Returns the value of the flags register
     * @return the value of the flags register
     */
    public byte getFlags() {
        return flags;
    }

    /**
     * Sets the value of the flags register
     * @param flags the value of the flags register
     */
    public void setFlags(byte flags) {
        this.flags = flags;
    }

    @Override
    public String toString() {
        return "Flags: " + (isZ() ? "Z" : "z")
                + (isN() ? "N" : "n")
                + (isH() ? "H" : "h")
                + (isC() ? "C" : "c")
                + " ----";
    }
}
