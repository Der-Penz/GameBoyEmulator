package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.cartridge.RAMSize;

public class Mbc1 implements AddressSpace {

    private final int[] cartridge;

    private final int ramBanks;

    private final int romBanks;

    private final int[][] ram;

    private boolean ramEnabled = false;
    private int selectedRamBank = 0;
    private int memoryModel = 0;

    public Mbc1(int[] cartridge, int romBanks, int ramBanks) {
        this.cartridge = cartridge;
        this.ramBanks = ramBanks;
        this.romBanks = romBanks;

        this.ram = new int[ramBanks][RAMSize.RAM_BANK_SIZE];

        this.ramEnabled = ramBanks > 0;
    }

    @Override
    public boolean accepts(int address) {
        return address >= 0x0000 && address <= 0x7FFF;
    }

    @Override
    public void writeByte(int address, int value) {
        if (address >= 0x0000 && address <= 0x1fff) {
            ramEnabled = (value & 0b1111) == 0xA;
            System.out.println(ramEnabled ? "RAM enabled" : "RAM disabled");
        } else if (address >= 0x2000 && address <= 0x3fff) {
            //select rom bank
        } else if (address >= 0x4000 && address <= 0x5fff) {
            if (memoryModel == 0) {
                //select rom bank
            } else {
                //select ram bank
                selectedRamBank = value & 0b11;
                System.out.printf("Selected RAM bank: %d\n", selectedRamBank);
            }
        }
    }

    @Override
    public int readByte(int address) {
        if (address >= 0xA000 && address <= 0xBFFF) {
            if (ramEnabled) {
                return getRamBank()[address - 0xA000];
            } else {
                return 0xFF;
            }
        }
        return 0;
    }

    private int[] getRamBank() {
        return ram[selectedRamBank];
    }

}
