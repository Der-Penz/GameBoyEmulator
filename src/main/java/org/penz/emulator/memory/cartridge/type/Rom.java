package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.memory.AddressSpace;

public class Rom implements AddressSpace {

    /**
     * rom data
     */
    private final int[] data;

    public Rom(int[] data) {
        this.data = data;
    }

    @Override
    public boolean accepts(int address) {
        return address >= 0x0000 && address <= 0x7FFF;
    }

    @Override
    public void writeByte(int address, int value) {
        throw new UnsupportedOperationException("Cannot write to ROM");
    }

    @Override
    public int readByte(int address) {
        return data[address];
    }
}