package org.penz.emulator.memory.cartridge;

import org.apache.commons.io.FilenameUtils;
import org.penz.emulator.IMemoryBankController;
import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.BootRom;
import org.penz.emulator.memory.cartridge.type.*;

import javax.swing.*;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Cartridge implements AddressSpace {

    /**
     * The boot rom of the Game boy
     */
    private final BootRom bootRom;
    /**
     * The data of the cartridge, now able to be memory bank switched
     */
    private final AddressSpace data;
    /**
     * The rom file of the Game
     */
    private final File romFile;
    private Battery battery;
    /**
     * Whether the boot rom is mapped to the beginning of the address space
     */
    private boolean bootRomEnabled;

    public Cartridge(File romFile) throws IOException {
        this.romFile = romFile;
        this.bootRom = new BootRom();
        this.bootRomEnabled = true;
        int[] rawData = loadRom(romFile);

        CartridgeType type = getCartridgeType(rawData);


        if (type.isMbc1()) {
            data = new Mbc1(rawData, getRomSize(rawData).numberOfBanks(), getRamSize(rawData).numberOfBanks());
        } else if (type.isMbc2()) {
            data = new Mbc2(rawData, getRomSize(rawData).numberOfBanks());
        } else if (type.isMbc3()) {
            data = new Mbc3(rawData, getRomSize(rawData).numberOfBanks(), getRamSize(rawData));
        } else if (type == CartridgeType.ROM_RAM || type == CartridgeType.ROM_RAM_BATTERY) {
            data = new RomRam(rawData);
        } else if (type == CartridgeType.ROM) {
            data = new Rom(rawData, 0x0000, 0x7FFF);
        } else {
            throw new UnsupportedOperationException("Unsupported cartridge type: " + type);
        }

        if (type.isBattery() || type.isRam()) {
            File saveFile = new File(romFile.getParent(), FilenameUtils.removeExtension(romFile.getName()) + ".sav");
            if (data instanceof IMemoryBankController) {
                battery = new Battery(saveFile, (IMemoryBankController) data);
                if (battery.saveAvailable()) {
                    try {
                        int romBanks = getRamSize(rawData).numberOfBanks();

                        // MBC2 only has 1 ram bank
                        if (type.isMbc2()) {
                            romBanks = 1;

                        // Additional ram bank for the clock register
                        } else if (type.isMbc3()) {
                            romBanks++;
                        }

                        var loadedRam = battery.loadRam(IMemoryBankController.RAM_MEMORY_START, romBanks);
                        ((IMemoryBankController) data).loadRam(loadedRam);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error loading save file: " + e.getMessage() + "\n continue without save", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
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
                return getDataFromZip(toLoad);
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
    }

    @Override
    public boolean accepts(int address) {
        if (bootRomEnabled && bootRom.accepts(address)) {
            return true;
        }
        return data.accepts(address) || address == 0xFF50;
    }

    @Override
    public void writeByte(int address, int value) {
        if (address == 0xFF50 && value == 1) {
            bootRomEnabled = false;
            return;
        }

        if (getCartridgeType() == CartridgeType.ROM) {
            return;
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

    public int getLicenseeCode() {
        int licenseeCodePos = 0x14B;
        return readByte(licenseeCodePos);
    }

    public int getChecksum() {
        return readByte(0x14D);
    }

    public int getGlobalChecksum() {
        return (readByte(0x14E) << 8) | readByte(0x14F);
    }

    public File getRomFile() {
        return romFile;
    }

    public int[] getDataFromZip(File zipFile) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry = zipInputStream.getNextEntry();

            while (entry != null) {
                if (!entry.isDirectory() && FilenameUtils.getExtension(entry.getName()).matches("gb|txt|bin")) {

                    int fileSize = (int) entry.getSize();
                    int[] rom = new int[fileSize];

                    try (DataInputStream dataInputStream = new DataInputStream(zipInputStream)) {
                        for (int i = 0; i < fileSize; i++) {
                            rom[i] = dataInputStream.read();
                        }
                    }

                    return rom;
                }

                entry = zipInputStream.getNextEntry();
            }
        }

        throw new IOException("No .gb, .txt or .bin file found in the zip archive: " + zipFile.getName());
    }

    /**
     * Saves the RAM of the cartridge to the save file if the cartridge has battery backed RAM
     *
     * @return true if the RAM was saved successfully, false if the cartridge has no battery backed RAM
     * @throws IOException if an error occurs while saving the RAM
     */
    public boolean saveRam() throws IOException {
        if (battery != null) {
            battery.flush();
            return true;
        }
        return false;
    }
}

