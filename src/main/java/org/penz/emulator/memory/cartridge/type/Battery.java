package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.Constants;
import org.penz.emulator.IMemoryBankController;
import org.penz.emulator.memory.Ram;
import org.penz.emulator.memory.cartridge.RAMSize;

import java.io.*;
import java.util.Arrays;

//TODO Battery save clock
public class Battery {
    private final File saveFile;
    private final IMemoryBankController mbc;

    public Battery(File saveFile, IMemoryBankController mbc) {
        this.saveFile = saveFile;
        this.mbc = mbc;
    }

    /**
     * Save given ram banks to the save file
     */
    private void saveRam(Ram[] ramToSave) throws IOException {
        if (!saveAvailable()) {
            if (!saveFile.createNewFile()) {
                throw new IOException("Could not create save file");
            }
        }

        try (OutputStream os = new FileOutputStream(saveFile)) {
            for (Ram ram : ramToSave) {
                int[] data = ram.getData();
                Arrays.stream(data).forEach(i -> {
                    try {
                        os.write((byte) i & 0xFF);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            System.out.println("Saved to " + saveFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load data from a save file and return the loaded ram banks
     * @return the loaded ram banks or null if the save file does not exist
     */
    public Ram[] loadRam(int offset, int numberOfBanks) {
        if (!saveAvailable()) {
            throw new RuntimeException("Save file does not exist");
        }

        Ram[] ramBanks = new Ram[numberOfBanks];

        try (InputStream is = new FileInputStream(saveFile)) {
            for (int i = 0; i < ramBanks.length; i++) {
                byte[] buffer = new byte[RAMSize.RAM_BANK_SIZE];
                if (is.read(buffer, i * RAMSize.RAM_BANK_SIZE, RAMSize.RAM_BANK_SIZE) == -1) {
                    throw new EOFException("End of file reached before all data was read");
                }
                Ram ram = bufferToRam(offset, buffer);
                ramBanks[i] = ram;
            }
            System.out.println("Loaded from " + saveFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ramBanks;
    }

    public void flush() throws IOException {
        if (mbc != null) {
            saveRam(mbc.flushRam());
        }
    }

    private Ram bufferToRam(int from, byte[] buffer) {
        int[] data = new int[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            data[i] = buffer[i] & Constants.BYTE_MAX_VALUE;
        }
        return new Ram(from, data);
    }

    public boolean saveAvailable() {
        return saveFile.exists() && saveFile.isFile();
    }
}
