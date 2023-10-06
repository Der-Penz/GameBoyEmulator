package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.memory.AddressSpace;

public class Mbc1 implements AddressSpace {

    private final int[] cartridge;

    private final int ramBanks;

    private final int romBanks;


    public Mbc1(int[] cartridge, int romBanks, int ramBanks) {
        this.cartridge = cartridge;
        this.ramBanks = ramBanks;
        this.romBanks = romBanks;
    }

    @Override
    public boolean accepts(int address) {
        return address >= 0x0000 && address <= 0x7FFF;
    }

    @Override
    public void writeByte(int address, int value) {

    }

    @Override
    public int readByte(int address) {
        return 0;
    }
}
