package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.IMemoryBankController;
import org.penz.emulator.memory.Ram;
import org.penz.emulator.memory.cartridge.RAMSize;

public class Mbc3 implements IMemoryBankController {

    private final Rom[] romBanks;

    private Ram[] ramBanks;

    private boolean ramEnabled = false;

    private boolean realTimeClockSelected = false;
    private int selectedRamBank = 0;
    private int selectedRomBank = 1;
    private RealTimeClock clock;
    private int latchClockReg = 0xff;

    public Mbc3(int[] cartridge, int romBanks, RAMSize ramSize) {
        this.romBanks = Rom.toRomBanks(romBanks, cartridge);

        if (this.ramBanks == null) {
            this.ramBanks = Ram.toRamBanks(ramSize.numberOfBanks(), 0xA000, 0xBFFF);
        }

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
            selectedRomBank = (value & 0b1111111);
            return;
        }

        if (address >= 0x4000 && address <= 0x5fff) {
            if (value >= 0x00 && value <= 0x03) {
                selectedRamBank = value;
            } else if (value >= 0x08 && value <= 0x0C) {
                clock.selectRegister(value);
            }
        }

        if (address >= 0x6000 && address <= 0x7fff) {
            if (value == 0x01 && latchClockReg == 0x00) {
                if (clock.isLatched()) {
                    clock.unlatch();
                } else {
                    clock.latch();
                }
            }
            latchClockReg = value;
        }

        if (address >= 0xA000 && address <= 0xBFFF) {
            if (!ramEnabled) {
                return;
            }

            if (realTimeClockSelected) {
                clock.setTimer(value);
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
                return clock.getTimer();
            } else {
                Ram ramBank = getRamBank();
                if (ramBank != null) {
                    return ramBank.readByte(address);
                }
                return 0xFF;
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

    @Override
    public Ram[] flushRam() {
        return ramBanks;
    }

    @Override
    public void loadRam(Ram[] ram) {
        System.arraycopy(ram, 0, ramBanks, 0, ramBanks.length);
    }
}
