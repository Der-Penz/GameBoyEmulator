package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;

public class Mbc1 implements AddressSpace {

    private final Rom[] romBanks;

    private final Ram[] ramBanks;

    private boolean ramEnabled;

    private int selectedRamBank = 0;
    private int selectedRomBank = 1;

    private int bankingMode = 0;

    public Mbc1(int[] cartridge, int romBanks, int ramBanks) {

        this.romBanks = Rom.toRomBanks(romBanks, cartridge);

        this.ramBanks = Ram.toRamBanks(ramBanks, 0xA000, 0xBFFF);
        this.ramEnabled = false;
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
            int lower5Bits = value & 0x1F;
            selectedRomBank = (selectedRomBank & 0b11100000) | lower5Bits;
            if (selectedRomBank == 0) {
                selectedRomBank++;
            }
            return;
        }

        if (address >= 0x4000 && address <= 0x5fff) {
            if (bankingMode == 0) {
                selectedRamBank = value & 0b11;
            } else {
                selectedRomBank &= 0x1F;
                value = value & 0b11100000;
                selectedRomBank |= value;
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
                Ram bank = getRamBank();
                if (bank != null) {
                    return bank.readByte(address);
                }
            }
            return 0xFF;
        }
        return 0;
    }

    private Ram getRamBank() {
        return ramBanks[selectedRamBank];
    }

    private Rom getRomBank() {
        return romBanks[selectedRomBank];
    }

}
