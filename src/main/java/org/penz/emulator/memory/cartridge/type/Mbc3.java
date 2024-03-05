package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;
import org.penz.emulator.memory.cartridge.RAMSize;
import org.penz.emulator.memory.cartridge.ROMSize;

import java.util.Arrays;

public class Mbc3 implements AddressSpace {

    private final Rom[] romBanks;

    private Ram[] ramBanks;

    private boolean ramEnabled = false;

    private boolean realTimeClockSelected = false;

    private int selectedRamBank = 0;
    private int selectedRomBank = 1;

    private Battery battery;

    private RealTimeClock realTimeClock;

    public Mbc3(int[] cartridge, int romBanks, int ramBanks, Battery battery) {

        this.battery = battery;
        this.romBanks = new Rom[romBanks];
        for (int i = 0; i < romBanks; i++) {
            int[] data = Arrays.copyOfRange(cartridge, i * ROMSize.ROM_BANK_SIZE, (i + 1) * ROMSize.ROM_BANK_SIZE);
            if (i == 0) {
                this.romBanks[i] = new Rom(data, 0, ROMSize.ROM_BANK_SIZE);
            } else {
                this.romBanks[i] = new Rom(data, ROMSize.ROM_BANK_SIZE, 2 * ROMSize.ROM_BANK_SIZE);
            }
        }

        if (battery != null) {
            var loadedBanks = battery.loadRam();
            if (loadedBanks != null) {
                this.ramBanks = loadedBanks;
            }
        }

        if (this.ramBanks == null) {
            this.ramBanks = new Ram[ramBanks];
            for (int i = 0; i < ramBanks; i++) {
                this.ramBanks[i] = new Ram(i * RAMSize.RAM_BANK_SIZE, ((i + 1) * RAMSize.RAM_BANK_SIZE) - 1);
            }
        }

        this.ramEnabled = false;
    }

    public Mbc3(int[] cartridge, int romBanks, int ramBanks) {
        this(cartridge, romBanks, ramBanks, null);

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
            selectedRomBank = (value & 0b1111111);
            return;
        }

        if (address >= 0x4000 && address <= 0x5fff) {
            if (value >= 0x00 && value <= 0x03) {
                selectedRamBank = value;
            } else if (value >= 0x08 && value <= 0x0C) {
                realTimeClock.selectRegister(value);
            }
        }

        if (address >= 0x6000 && address <= 0x7fff) {
            realTimeClock.toggleLatch(value);
        }

        if (address >= 0xA000 && address <= 0xBFFF) {
            if (!ramEnabled) {
                return;
            }

            if (realTimeClockSelected) {
                realTimeClock.setSelectedRegister(value);
            } else {
                getRamBank().writeByte(address, value);
            }
        }
    }

    @Override
    public int readByte(int address) {
        if (address >= 0x0000 && address <= 0x3fff) {
            return romBanks[0].readByte(address);
        }

        if (address >= 0x4000 && address <= 0x7fff) {
            return getRomBank().readByte(address);
        }

        if (address >= 0xA000 && address <= 0xBFFF) {
            if (!ramEnabled) {
                return 0xFF;
            }

            if (realTimeClockSelected) {
                return realTimeClock.getSelectedRegister();
            } else {
                return getRamBank().readByte(address);
            }
        }

        return 0xFF;
    }

    private Ram getRamBank() {
        return ramBanks[selectedRamBank];
    }

    private Rom getRomBank() {
        return romBanks[selectedRomBank == 0 ? 1 : selectedRomBank];
    }

}
