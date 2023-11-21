package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;
import org.penz.emulator.memory.cartridge.RAMSize;
import org.penz.emulator.memory.cartridge.ROMSize;

public class Mbc1 implements AddressSpace {

    private final Rom[] romBanks;

    private final Ram[] ramBanks;

    private boolean ramEnabled = false;

    private int selectedRamBank = 0;
    private int selectedRomBank = 0;

    private int bankingMode = 0;

    public Mbc1(int[] cartridge, int romBanks, int ramBanks) {

        this.romBanks = new Rom[romBanks];
        for (int i = 0; i < romBanks; i++) {
            this.romBanks[i] = new Rom(cartridge, i * ROMSize.ROM_BANK_SIZE, ((i + 1) * ROMSize.ROM_BANK_SIZE) - 1);
        }

        this.ramBanks = new Ram[ramBanks];
        for (int i = 0; i < ramBanks; i++) {
            this.ramBanks[i] = new Ram(i * RAMSize.RAM_BANK_SIZE, ((i + 1) * RAMSize.RAM_BANK_SIZE) - 1);
        }

        this.ramEnabled = ramBanks > 0;
    }

    @Override
    public boolean accepts(int address) {
        return address >= 0x0000 && address <= 0x7FFF;
    }

    @Override
    public void writeByte(int address, int value) {
        if (address >= 0x0000 && address <= 0x1fff) {
            ramEnabled = (value & 0xF) == 0xA;
            return;
        }

        if (address >= 0x2000 && address <= 0x3fff) {
            selectedRomBank = (value & 0b11111) & (romBanks.length - 1);
            return;
        }

        if (address >= 0x4000 && address <= 0x5fff) {
            if (bankingMode == 0) {
                selectedRamBank = value & 0b11;
            } else {
                selectedRomBank = ((value & 0b11) << 5) + selectedRamBank;
            }
            return;
        }

        if (address >= 0x6000 && address <= 0x7fff) {
            bankingMode = (value & 0x1);
        }
    }

    @Override
    public int readByte(int address) {
        if (address >= 0x0000 && address <= 0x3FFF) {
            return romBanks[0].readByte(address);
        }

        if (address >= 0x4000 && address <= 0x7FFF) {
            return getRomBank().readByte(address);
        }

        if (address >= 0xA000 && address <= 0xBFFF) {
            if (ramEnabled) {
                return getRamBank().readByte(address);
            } else {
                return 0xFF;
            }
        }
        return 0;
    }

    private Ram getRamBank() {
        return ramBanks[selectedRamBank];
    }

    private Rom getRomBank() {
        return romBanks[selectedRomBank == 0 ? 1 : selectedRomBank];
    }

}
