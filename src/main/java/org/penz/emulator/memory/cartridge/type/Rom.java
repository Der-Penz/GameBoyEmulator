package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.memory.AddressSpace;

/**
 * Basic implementation of a read-only memory address space
 */
public class Rom implements AddressSpace {

    private final int[] data;

    private final int start;

    public Rom(int[] data) {
        this(data, 0, data.length);
    }

    public Rom(int[] data, int from, int to) {
        this.start = from;
        this.data = new int[to - from + 1];
        System.arraycopy(data, 0, this.data, 0, Math.min(to - from, data.length));
    }

    @Override
    public boolean accepts(int address) {
        return address >= start && address <= data.length - 1;
    }

    @Override
    public void writeByte(int address, int value) {
        //address to hex
        throw new UnsupportedOperationException("Cannot write to ROM. " + BitUtil.toHex(address));
    }

    @Override
    public int readByte(int address) {
        return data[address];
    }
}
