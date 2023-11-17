package org.penz.emulator.memory.cartridge;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.BootRom;
import org.penz.emulator.memory.cartridge.type.Mbc1;
import org.penz.emulator.memory.cartridge.type.Rom;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Cartridge implements AddressSpace {

    private final int[] rawData;

    private final BootRom bootRom;

    private final AddressSpace data;

    /**
     * Whether the boot rom is mapped to the beginning of the address space
     */
    private boolean bootRomEnabled;

    public Cartridge(File romFile) throws IOException {
        this.bootRom = new BootRom();
        this.bootRomEnabled = true;
        this.rawData = loadRom(romFile);

        CartridgeType type = this.getCartridgeType();

        if (type.isMbc1()) {
            data = new Mbc1(rawData, getRomSize().numberOfBanks(), getRamSize().numberOfBanks());
        } else {
            if (type == CartridgeType.ROM) {
                data = new Rom(rawData);
            } else {
                throw new UnsupportedOperationException("Unsupported cartridge type: " + type);
            }
        }

    }

    /**
     * Loads a rom file
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
        return data.accepts(address) || address == 0xFF50;
    }

    @Override
    public void writeByte(int address, int value) {
        if (address == 0xFF50 && value == 1) {
            bootRomEnabled = false;
        }

        data.writeByte(address, value);
    }

    @Override
    public int readByte(int address) {
        if (bootRomEnabled && bootRom.accepts(address)) {
            return bootRom.readByte(address);
        }
        return data.readByte(address);
    }

    /**
     * Get the game name from the cartridge (0x134 - 0x142)
     *
     * @return the game name
     */
    public String getTitle() {
        int length = 15;
        int start = 0x134;

        return new String(rawData, start, length).trim();
    }

    /**
     * Check if the Cartridge is a GameBoy Color cartridge (0x143)
     *
     * @return
     */
    public CGBFlag isColorGameBoy() {
        int colorGameBoyFlag = 0x143;
        return CGBFlag.fromValue(rawData[colorGameBoyFlag]);
    }

    public ROMSize getRomSize() {
        int romSizePos = 0x148;
        return ROMSize.fromValue(rawData[romSizePos]);
    }

    public RAMSize getRamSize() {
        int ramSizePos = 0x149;
        return RAMSize.fromValue(rawData[ramSizePos]);
    }

    public DestinationCode getDestinationCode() {
        int destinationCodePos = 0x14A;
        return DestinationCode.fromValue(rawData[destinationCodePos]);
    }

    public CartridgeType getCartridgeType() {
        int cartridgeTypePos = 0x147;
        return CartridgeType.fromValue(rawData[cartridgeTypePos]);
    }
}

