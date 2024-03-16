package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.cartridge.ROMSize;

import java.util.Arrays;

/**
 * Basic implementation of a read-only memory address space
 */
public class Rom implements AddressSpace {

    private final int[] data;

    private final int start;

    /**
     * Creates a new ROM with the given data starting at address 0 and ending at the end of the data
     *
     * @param data the data
     */
    public Rom(int[] data) {
        this(data, 0, data.length - 1);
    }

    /**
     * Creates a new ROM with the given data. If the data is larger than the given range, it will be truncated from the start.
     *
     * @param data the data
     * @param from the start address
     * @param to   the end address, inclusive
     */
    public Rom(int[] data, int from, int to) {
        this.start = from;

        if (to - from + 1 < data.length) {
            this.data = Arrays.copyOfRange(data, from, to);
        } else {
            this.data = data;
        }
    }

    /**
     * Converts cartridge data into a given number of ROM banks
     *
     * @param count     the number of ROM banks
     * @param cartridge the cartridge data
     * @return
     */
    public static Rom[] toRomBanks(int count, int[] cartridge) {
        Rom[] romBanks = new Rom[count];
        for (int i = 0; i < count; i++) {
            int[] data = Arrays.copyOfRange(cartridge, i * ROMSize.ROM_BANK_SIZE, (i + 1) * ROMSize.ROM_BANK_SIZE);
            if (i == 0) {
                romBanks[i] = new Rom(data, 0, ROMSize.ROM_BANK_SIZE);
            } else {
                romBanks[i] = new Rom(data, ROMSize.ROM_BANK_SIZE, 2 * ROMSize.ROM_BANK_SIZE);
            }
        }
        return romBanks;
    }

    @Override
    public boolean accepts(int address) {
        return address >= start && address <= data.length - 1;
    }

    @Override
    public void writeByte(int address, int value) {
        //address to hex
        throw new UnsupportedOperationException("Cannot write to ROM Address: 0x" + BitUtil.toHex(address));
    }

    @Override
    public int readByte(int address) {
        return data[address - start];
    }
}
