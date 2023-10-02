package org.penz.emulator.cpu.opcode;

public enum DataType {
    /**
     * 8 bit immediate data
     */
    d8,
    /**
     * 16 bit immediate data
     */
    d16,
    /**
     * 8 bit signed data, which is added to program counter
     */
    r8,
    /**
     * 8 bit unsigned data, which are added to $FF00 in certain instructions
     */
    a8,
    /**
     * 16 bit address
     */
    a16
}
