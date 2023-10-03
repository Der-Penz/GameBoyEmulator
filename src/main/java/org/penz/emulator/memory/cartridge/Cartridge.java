package org.penz.emulator.memory.cartridge;

import org.penz.emulator.memory.AddressSpace;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Cartridge implements AddressSpace {

    private final int[] data;

    public Cartridge(File romFile) throws IOException {
        this.data = loadRom(romFile);
    }

    /**
     * Load a rom file
     *
     * @param toLoad the rom file to load, either a .gb or .zip file
     * @return the rom data as an array of bytes
     * @throws IOException if an error occurs while loading the rom file
     */
    private int[] loadRom(File toLoad) throws IOException {
        String fileExtension = toLoad.getName().substring(toLoad.getName().lastIndexOf(".") + 1);

        try (FileInputStream stream = new FileInputStream(toLoad); DataInputStream dataStream = new DataInputStream(stream)) {
            if (fileExtension.equals("zip")) {
                //TODO implement zip loading
            } else {
                long fileSize = toLoad.length();
                int[] rom = new int[(int) fileSize];

                for (int i = 0; i < fileSize; i++) {
                    rom[i] = dataStream.read();
                }

                return rom;
            }


        } catch (IOException e) {
            throw new IOException("Error while loading rom file: " + toLoad.getName(), e);
        }

        return new int[0];
    }

    @Override
    public boolean accepts(int address) {
        //TODO implement cartridge memory bank switching and correct address range
        return address >= 0x0100 && address <= 0x7FFF;
    }

    @Override
    public void writeByte(int address, int value) {
        data[address] = value;
    }

    @Override
    public int readByte(int address) {
        return data[address];
    }

    /**
     * Get the game name from the cartridge (0x134 - 0x142)
     *
     * @return the game name
     */
    public String getTitle() {
        int length = 15;
        int start = 0x134;

        return new String(data, start, length).trim();
    }

    /**
     * Check if the Cartridge is a GameBoy Color cartridge (0x143)
     *
     * @return
     */
    public CGBFlag isColorGameBoy() {
        int colorGameBoyFlag = 0x143;
        return CGBFlag.fromValue(data[colorGameBoyFlag]);
    }

    public ROMSize getRomSize() {
        int romSizePos = 0x148;
        return ROMSize.fromValue(data[romSizePos]);
    }

    public DestinationCode getDestinationCode() {
        int destinationCodePos = 0x14A;
        return DestinationCode.fromValue(data[destinationCodePos]);
    }

}

