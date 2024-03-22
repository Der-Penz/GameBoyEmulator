package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.IMemoryBankController;
import org.penz.emulator.memory.Ram;

/**
 * MBC2 memory bank controller
 */
public class Mbc2 implements IMemoryBankController {

    private final Rom[] romBanks;

    private Ram ramBank;

    private boolean ramEnabled;

    private int selectedRomBank = 1;

    public Mbc2(int[] cartridge, int romBanks) {
        if (romBanks > 16) {
            throw new IllegalArgumentException("MBC2 only supports up to 16 ROM banks");
        }
        this.ramEnabled = false;
        this.romBanks = Rom.toRomBanks(romBanks, cartridge);
        this.ramBank = new Ram(0xA000, 0xA1FF);
    }

    @Override
    public boolean accepts(int address) {
        return (address >= 0x0000 && address <= 0x7FFF) || (address >= 0xA000 && address <= 0xBFFF);
    }

    @Override
    public void writeByte(int address, int value) {
        if (address >= 0x0000 && address <= 0x3FFF) {
            if ((address & 0x0100) == 0) {
                ramEnabled = value == 0xA;
                System.out.println("RAM enabled: " + ramEnabled);
            } else {
                selectedRomBank = value & 0b1111;
            }
            return;
        }

        if (address >= 0xA000 && address <= 0xBFFF) {
            if (ramEnabled) {
                ramBank.writeByte(translateRamAddress(address), value & 0xF);
            }
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
                return ramBank.readByte(translateRamAddress(address));
            } else {
                return 0x0F;
            }
        }
        return 0;
    }

    private int translateRamAddress(int address) {
        return ((address - 0xA000) % 0x200) + 0xA000;
    }

    private Rom getRomBank() {
        return romBanks[selectedRomBank == 0 ? 1 : selectedRomBank];
    }

    @Override
    public Ram[] flushRam() {
        return new Ram[]{ramBank};
    }

    @Override
    public void loadRam(Ram[] ram) {
        ramBank = ram[0];
    }
}
