package org.penz.emulator.graphics;

import org.penz.emulator.graphics.enums.ObjSize;
import org.penz.emulator.memory.AddressSpace;

public class Oam implements AddressSpace {


    private final AddressSpace memory;
    private final int[] data = new int[160];

    private int dmaRegister;

    public Oam(AddressSpace memory) {
        this.memory = memory;
    }

    /**
     * Performs a DMA transfer from the given address
     *
     * @param sourceAddress the address to transfer from
     */
    public void doDMATransfer(int sourceAddress) {
        if ((sourceAddress & 0xFF) > 0xDF) {
            throw new IllegalArgumentException("Invalid DMA transfer sourceAddress 0x" +
                    Integer.toHexString(sourceAddress) + ". Must be in range 0x00-0xDF");
        }
        int startAddress = sourceAddress * 0x100;
        dmaRegister = (startAddress >> 8) & 0xFF;

        for (int i = 0; i < 160; i++) {
            writeByte(0xFE00 + i, memory.readByte(startAddress + i));
        }

    }

    /**
     * Performs an OAM scan and returns the data for the sprites on the given scanline
     *
     * @param lcy  the current scanline
     * @param size the size of the sprites
     * @return the data for the sprites on the given scanline
     */
    public int[] doOAMScan(int lcy, ObjSize size) {
        int[] spritesData = new int[40];
        int spritesOnScanline = 0;
        for (int i = 0; i < data.length; i += 4) {
            int yPosition = data[i] - 16;
            int sizeOffset = size == ObjSize.SIZE_8x16 ? 16 : 8;
            if (yPosition <= lcy && yPosition + sizeOffset > lcy) {
                spritesOnScanline++;
                System.arraycopy(data, i, spritesData, (spritesOnScanline - 1) * 4, 4);
            }
            if (spritesOnScanline == 10) {
                break;
            }
        }

        return spritesData;
    }

    @Override
    public boolean accepts(int address) {
        return address >= 0xFE00 && address <= 0xFE9F || address == 0xFF46;
    }

    @Override
    public void writeByte(int address, int value) {
        if (address == 0xFF46) {
            doDMATransfer(value);
            return;
        }
        data[address - 0xFE00] = value;
    }

    @Override
    public int readByte(int address) {
        if (address == 0xFF46) {
            return dmaRegister;
        }
        return data[address - 0xFE00];
    }
}
