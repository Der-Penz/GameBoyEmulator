package org.penz.emulator.graphics;

import org.penz.emulator.memory.AddressSpace;

public class Ppu implements AddressSpace {

    private LcdRegister lcdRegister;

    @Override
    public boolean accepts(int address) {
        return false;
    }

    @Override
    public void writeByte(int address, int value) {

    }

    @Override
    public int readByte(int address) {
        return 0;
    }
}
