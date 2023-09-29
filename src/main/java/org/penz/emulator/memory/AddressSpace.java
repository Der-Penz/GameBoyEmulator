package org.penz.emulator.memory;

/**
 * Defines memory that can be read from and written to
 */
public interface AddressSpace {

    /**
     * Check if the address is in the range of this memory bank
     * @param address to check
     * @return true if the address is in the range of this memory bank
     */
    boolean accepts(int address);

    /**
     * Write a byte to the given address
     * @param address to write to
     */
    void writeByte(int address);

    /**
     * Read a byte from the given address
     * @param address to read from
     * @return byte read from the given address
     */
    int readByte(int address);
}
