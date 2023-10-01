package org.penz.emulator.memory;

import org.penz.emulator.Constants;

import java.io.*;

/**
 * Boot Rom for the GameBoy
 */
public class BootRom implements AddressSpace {

    private final int[] data;

    /**
     * Create a new boot rom
     * defaults to GameBoy boot rom
     * TODO allow to load custom boot roms (e.g. for GameBoy Color)
     * @throws FileNotFoundException if the boot rom file is not found
     */
    public BootRom() throws FileNotFoundException {
        File file = new File(Constants.GB_BOOT_ROM_PATH);

        if (!file.exists()) {
            throw new FileNotFoundException("Boot rom file not found");
        }

        try (FileInputStream stream = new FileInputStream(file); DataInputStream dataStream = new DataInputStream(stream)) {

            long fileSize = file.length();
            data = new int[(int) fileSize];

            for (int i = 0; i < fileSize; i++) {
                data[i] = dataStream.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean accepts(int address) {
        return address >= 0x0000 && address <= 0x00FF;
    }

    @Override
    public void writeByte(int address, int value) {
        throw new UnsupportedOperationException("Cannot write to boot rom. Read only.");
    }

    @Override
    public int readByte(int address) {
        return data[address];
    }
}
