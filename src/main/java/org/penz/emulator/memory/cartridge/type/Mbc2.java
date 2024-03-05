package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;
import org.penz.emulator.memory.cartridge.ROMSize;

import java.util.Arrays;

/**
 * MBC2 memory bank controller
 */
public class Mbc2 implements AddressSpace {

    private final Rom[] romBanks;

    private final Ram ramBank;

    private boolean ramEnabled = false;

    private int selectedRomBank = 1;

    public Mbc2(int[] cartridge, int romBanks, int ramBanks) {

        if (romBanks > 16) {
            throw new IllegalArgumentException("MBC2 only supports up to 16 ROM banks");
        }

        this.romBanks = new Rom[romBanks];
        for (int i = 0; i < romBanks; i++) {
            int[] data = Arrays.copyOfRange(cartridge, i * ROMSize.ROM_BANK_SIZE, (i + 1) * ROMSize.ROM_BANK_SIZE);
            if (i == 0) {
                this.romBanks[i] = new Rom(data, 0, ROMSize.ROM_BANK_SIZE);
            } else {
                this.romBanks[i] = new Rom(data, ROMSize.ROM_BANK_SIZE, 2 * ROMSize.ROM_BANK_SIZE);
            }
        }

        ramBank = new Ram(0xA000, 0xA1FF);

        this.ramEnabled = false;
    }

    @Override
    public boolean accepts(int address) {
        return address >= 0x0000 && address <= 0x7FFF;
    }

    @Override
    public void writeByte(int address, int value) {
        if (address >= 0x0000 && address <= 0x3FFF) {
            if ((address & 0x0100) == 0) {
                ramEnabled = (value & 0xF) == 0xA;
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

}
