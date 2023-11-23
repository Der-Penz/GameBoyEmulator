package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;
import org.penz.emulator.memory.cartridge.RAMSize;
import org.penz.emulator.memory.cartridge.ROMSize;

public class Mbc3 implements AddressSpace {

    private final Rom[] romBanks;

    private final Ram[] ramBanks;

    private boolean ramEnabled = false;

    private int selectedRamBank = 0;
    private int selectedRomBank = 1;

    public Mbc3(int[] cartridge, int romBanks, int ramBanks) {

        this.romBanks = new Rom[romBanks];
        for (int i = 0; i < romBanks; i++) {
            this.romBanks[i] = new Rom(cartridge, i * ROMSize.ROM_BANK_SIZE, ((i + 1) * ROMSize.ROM_BANK_SIZE) - 1);
        }

        this.ramBanks = new Ram[ramBanks];
        for (int i = 0; i < ramBanks; i++) {
            this.ramBanks[i] = new Ram(i * RAMSize.RAM_BANK_SIZE, ((i + 1) * RAMSize.RAM_BANK_SIZE) - 1);
        }

        this.ramEnabled = false;
    }

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
