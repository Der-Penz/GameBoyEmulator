package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.memory.Ram;
import org.penz.emulator.memory.cartridge.RAMSize;

import java.io.*;

//TODO Battery save clock
public class Battery {

    private final RAMSize ramSize;
    private final File filePath;

    public Battery(File filePath, RAMSize ramSize) {
        this.filePath = filePath;
        this.ramSize = ramSize;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\mike2\\OneDrive\\Desktop\\test2.sav");

        Battery battery = new Battery(file, RAMSize.KB_8);
        Ram[] ramBanks = battery.loadRam();
        battery.saveRam(new Ram[]{new Ram(0, new int[]{2, 4, 3, 4, 5, 6, 7, 8})});
    }

    /**
     * Save data from given ram
     */
    public void saveRam(Ram[] ramBanks) throws FileNotFoundException {
        File save;
        //check if in the filepath dir a .sav file exists
        if (filePath.isDirectory()) {
            File[] files = filePath.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".sav")) {
                        save = file;
                        break;
                    }
                }
            }
            save = new File(filePath, "save.sav");
        } else {
            throw new FileNotFoundException("The file path is not a directory");
        }


        try (OutputStream os = new FileOutputStream(save)) {
            for (Ram ram : ramBanks) {
                int i = ram.getOffset();
                while (ram.accepts(i)) {
                    byte data = (byte) (ram.readByte(i) & 0xFF);
                    os.write(data);
                    i++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load data into ram
     *
     * @return the loaded ram banks or null if the save file does not exist
     */
    public Ram[] loadRam() {
        //check for a .sav file in the file path
        File save = new File(filePath, "save.sav");
        if (!save.exists()) {
            return null;
        }

        Ram[] ramBanks = new Ram[ramSize.numberOfBanks()];
        try (InputStream is = new FileInputStream(save)) {
            for (int i = 0; i < ramBanks.length; i++) {
                byte[] buffer = new byte[RAMSize.RAM_BANK_SIZE];
                is.read(buffer, i * RAMSize.RAM_BANK_SIZE, RAMSize.RAM_BANK_SIZE);
                Ram ram = bufferToRam(i * RAMSize.RAM_BANK_SIZE, buffer);
                ramBanks[i] = ram;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ramBanks;
    }

    private Ram bufferToRam(int from, byte[] buffer) {
        //make every byte from the buffer into an int
        int[] data = new int[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            data[i] = buffer[i];
        }
        return new Ram(from, data);
    }
}
