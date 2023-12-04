package org.penz.emulator.graphics;

import org.penz.emulator.Constants;
import org.penz.emulator.memory.AddressSpace;

public class Oam implements AddressSpace {


    private final AddressSpace memory;
    private final int[] data = new int[160];

    public Oam(AddressSpace memory) {
        this.memory = memory;
    }

    /**
     * Performs a DMA transfer from the given address
     *
     * @param sourceAddress the address to transfer from
     */
    public void doDMATransfer(int sourceAddress) {
        if ((sourceAddress & 0xFF) > 0xDF) {
            throw new IllegalArgumentException("Invalid DMA transfer sourceAddress 0x" +
                    Integer.toHexString(sourceAddress) + ". Must be in range 0x00-0xDF");
        }

        int startAddress = (sourceAddress << 8) % Constants.WORD_MAX_VALUE;

        for (int i = 0; i < 160; i++) {
            writeByte(0xFE00 + i, memory.readByte(startAddress + i));
        }

    }

    @Override
    public boolean accepts(int address) {
        return address >= 0xFE00 && address <= 0xFE9F;
    }

    @Override
    public void writeByte(int address, int value) {
        data[address - 0xFE00] = value;
    }

    @Override
    public int readByte(int address) {
        return data[address - 0xFE00];
    }
}
