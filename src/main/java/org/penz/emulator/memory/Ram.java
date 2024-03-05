package org.penz.emulator.memory;

public class Ram implements AddressSpace {

    private final int[] data;

    private final int offset;

    /**
     * Create a new RAM from the given address range
     *
     * @param from start address, inclusive
     * @param to   end address, inclusive
     */
    public Ram(int from, int to) {
        this.offset = from;
        data = new int[to - from + 1];
    }

    public Ram(int from, int[] data) {
        this.offset = from;
        this.data = data;
    }

    @Override
    public boolean accepts(int address) {
        return address >= offset && address <= offset + data.length - 1;
    }

    @Override
    public void writeByte(int address, int value) {
        data[address - offset] = value;
    }

    @Override
    public int readByte(int address) {
        return data[address - offset];
    }

    public int getOffset() {
        return offset;
    }
}
