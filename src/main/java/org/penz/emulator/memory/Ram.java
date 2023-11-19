package org.penz.emulator.memory;

public class Ram implements AddressSpace {

    private final int[] data;

    private final int offset;

    public Ram(int from, int to) {
        this.offset = from;
        data = new int[to - from + 1];
    }

    @Override
    public boolean accepts(int address) {
        return address >= offset && address <= offset + data.length;
    }

    @Override
    public void writeByte(int address, int value) {
        data[address - offset] = value;
    }

    @Override
    public int readByte(int address) {
        return data[address - offset];
    }
}
