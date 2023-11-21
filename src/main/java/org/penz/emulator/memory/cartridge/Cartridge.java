package org.penz.emulator.memory.cartridge;

import org.apache.commons.io.FilenameUtils;
import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.BootRom;
import org.penz.emulator.memory.cartridge.type.Mbc1;
import org.penz.emulator.memory.cartridge.type.Mbc2;
import org.penz.emulator.memory.cartridge.type.Rom;

import java.io.*;

public class Cartridge implements AddressSpace {

    /**
     * The raw data of the cartridge. Should not be used directly, only for reading the headers
     */
    private final int[] rawData;

    /**
     * The boot rom of the Game boy
     */
    private final BootRom bootRom;

    /**
     * The data of the cartridge, now able to be memory bank switched
     */
    private final AddressSpace data;

    /**
     * Whether the boot rom is mapped to the beginning of the address space
     */
    private boolean bootRomEnabled;

    public Cartridge(File romFile) throws IOException {
        this.bootRom = new BootRom();
        this.bootRomEnabled = true;
        rawData = loadRom(romFile);

        CartridgeType type = getCartridgeType(rawData);

        if (type.isMbc1()) {
            data = new Mbc1(rawData, getRomSize(rawData).numberOfBanks(), getRamSize(rawData).numberOfBanks());
        } else if (type.isMbc2()) {
            data = new Mbc2(rawData, getRomSize(rawData).numberOfBanks(), getRamSize(rawData).numberOfBanks());
        } else if (type == CartridgeType.ROM) {
            data = new Rom(rawData, 0x0000, 0x7FFFF);
        } else {
            throw new UnsupportedOperationException("Unsupported cartridge type: " + type);
        }
    }

    /**
     * Loads a rom file
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

    public String getTitle() {
        int length = 15;
        int start = 0x134;

        return new String(AddressSpace.readRange(this, start, length), 0, length).trim();
    }

    public CGBFlag isColorGameBoy() {
        int colorGameBoyFlag = 0x143;
        return CGBFlag.fromValue(readByte(colorGameBoyFlag));
    }

    public ROMSize getRomSize() {
        int romSizePos = 0x148;
        return ROMSize.fromValue(readByte(romSizePos));
    }

    public ROMSize getRomSize(int[] data) {
        int romSizePos = 0x148;
        return ROMSize.fromValue(data[romSizePos]);
    }

    public RAMSize getRamSize() {
        int ramSizePos = 0x149;
        return RAMSize.fromValue(readByte(ramSizePos));
    }

    public RAMSize getRamSize(int[] data) {
        int ramSizePos = 0x149;
        return RAMSize.fromValue(data[ramSizePos]);
    }

    public DestinationCode getDestinationCode() {
        int destinationCodePos = 0x14A;
        return DestinationCode.fromValue(readByte(destinationCodePos));
    }

    public CartridgeType getCartridgeType() {
        int cartridgeTypePos = 0x147;
        return CartridgeType.fromValue(readByte(cartridgeTypePos));
    }

    public CartridgeType getCartridgeType(int[] data) {
        int cartridgeTypePos = 0x147;
        return CartridgeType.fromValue(data[cartridgeTypePos]);
    }

    public int getChecksum() {
        return readByte(0x14D);
    }

    public int getGlobalChecksum() {
        return (readByte(0x14E) << 8) | readByte(0x14F);
    }

    /**
     * Load a cartridge by a given file path
     *
     * @param path the path to the rom file
     * @return the loaded cartridge
     * @throws FileNotFoundException         if the file is not found
     * @throws IOException                   if an error occurs while loading the rom file
     * @throws UnsupportedOperationException if the cartridge type is not supported
     */
    public static Cartridge loadCartridge(String path) throws IOException {
        File romFile = new File(path);

        if (!romFile.exists()) {
            throw new FileNotFoundException("File not found: " + path);
        }

        if (!FilenameUtils.getExtension(romFile.getName()).matches("gb|txt|bin|zip")) {
            throw new IllegalArgumentException("Unsupported file type: " + FilenameUtils.getExtension(romFile.getName()));
        }

        return new Cartridge(romFile);
    }
}

