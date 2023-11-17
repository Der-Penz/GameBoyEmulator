package org.penz.emulator;

import org.apache.commons.io.FilenameUtils;
import org.penz.emulator.cpu.Cpu;
import org.penz.emulator.cpu.InterruptManager;
import org.penz.emulator.memory.BootRom;
import org.penz.emulator.memory.Mmu;
import org.penz.emulator.memory.cartridge.Cartridge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameBoy {

    private final Cpu cpu;

    private final Mmu mmu;

    private Cartridge cartridge;

    public GameBoy(String romPath) throws IOException {
        this.mmu = new Mmu();
        this.mmu.addMemoryBank(new BootRom());
        this.mmu.addMemoryBank(loadCartridge(romPath));
        this.mmu.indexBanks();
        this.cpu = new Cpu(mmu, new InterruptManager());
    }

    public static void main(String[] args) throws IOException {
        var romPath = "src/main/resources/roms/tetris.gb";

        var gameBoy = new GameBoy(romPath);
        gameBoy.start();
    }

    /**
     * Load a cartridge by a given file path
     *
     * @param path the path to the rom file
     * @throws FileNotFoundException         if the file is not found
     * @throws IOException                   if an error occurs while loading the rom file
     * @throws UnsupportedOperationException if the cartridge type is not supported
     */
    public Cartridge loadCartridge(String path) throws IOException {
        File romFile = new File(path);

        if (!romFile.exists()) {
            throw new FileNotFoundException("File not found: " + path);
        }

        if (!FilenameUtils.getExtension(romFile.getName()).matches("gb|txt|bin|zip")) {
            throw new IllegalArgumentException("Unsupported file type: " + FilenameUtils.getExtension(romFile.getName()));
        }

        cartridge = new Cartridge(romFile);
        return cartridge;
    }

    public void start() {
        while (true) {
            cpu.tick();
        }
    }

}