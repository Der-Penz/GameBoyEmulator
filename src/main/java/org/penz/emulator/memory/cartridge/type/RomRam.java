package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.IMemoryBankController;
import org.penz.emulator.memory.Ram;

/**
 * Basic implementation of a cartridge with ROM and RAM
 * No mapper is used, so the ROM is always mapped to 0x0000-0x7FFF and the RAM to 0xA000-0xBFFF
 */
public class RomRam implements IMemoryBankController {

    private Ram ram;
    private final Rom rom;

    public RomRam(int[] rawData) {
        rom = new Rom(rawData, 0x0000, 0x7FFF);
        ram = new Ram(IMemoryBankController.RAM_MEMORY_START, IMemoryBankController.RAM_MEMORY_END);
    }

    @Override
    public Ram[] flushRam() {
        return new Ram[]{ram};
    }

    @Override
    public void loadRam(Ram[] ram) {
        this.ram = ram[0];
    }

    @Override
    public boolean accepts(int address) {
        return ram.accepts(address) || rom.accepts(address);
    }

    @Override
    public void writeByte(int address, int value) {
        if (ram.accepts(address)) {
            ram.writeByte(address, value);
        }
    }

    @Override
    public int readByte(int address) {
        if (ram.accepts(address)) {
            return ram.readByte(address);
        }
        if (rom.accepts(address)) {
            return rom.readByte(address);
        }
        return 0xFF;
    }
}
