package org.penz.emulator;

import org.apache.commons.io.FilenameUtils;
import org.penz.emulator.cpu.Cpu;
import org.penz.emulator.cpu.Timer;
import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.input.ButtonController;
import org.penz.emulator.input.Joypad;
import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.BootRom;
import org.penz.emulator.memory.Mmu;
import org.penz.emulator.memory.cartridge.Cartridge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameBoy {

    public static final int CLOCK_SPEED = 4194304;
    private final Cpu cpu;

    private final Mmu mmu;

    private final Timer timer;

    private Cartridge cartridge;

    public GameBoy(String romPath, ButtonController controls) throws IOException {

        cartridge = loadCartridge(romPath);

        if (cartridge.isColorGameBoy() == CGBFlag.CGB_ONLY) {
            throw new UnsupportedOperationException("GameBoy Color is not supported yet");
        }

        InterruptManager interruptManager = new InterruptManager();
        this.timer = new Timer(interruptManager);
        Ram ram = new Ram(0xC000, 0xDFFF);

        this.mmu = new Mmu();
        this.mmu.addMemoryBank(new BootRom());
        this.mmu.addMemoryBank(cartridge);
        this.mmu.addMemoryBank(ram);
        this.mmu.addMemoryBank(new EchoAddressSpace(ram, 0xC000, 0xE000, 0xFDFF));
        this.mmu.addMemoryBank(new Joypad(interruptManager, controls));
        this.mmu.addMemoryBank(interruptManager);
        this.mmu.addMemoryBank(this.timer);
        this.mmu.indexBanks();
        this.cpu = new Cpu(mmu, interruptManager);
    }

    public static void main(String[] args) throws IOException {
        var romPath = "src/main/resources/roms/tetris.gb";

        var gameBoy = new GameBoy(romPath, null);
        gameBoy.start();
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
    public Cartridge loadCartridge(String path) throws IOException {
        File romFile = new File(path);

        if (!romFile.exists()) {
            throw new FileNotFoundException("File not found: " + path);
        }

        if (!FilenameUtils.getExtension(romFile.getName()).matches("gb|txt|bin|zip")) {
            throw new IllegalArgumentException("Unsupported file type: " + FilenameUtils.getExtension(romFile.getName()));
        }

        return new Cartridge(romFile);
    }

    public void start() {
        while (true) {
            System.out.println("gameboy clock tick");
            tick();
        }
    }

    public void tick() {
        timer.tick();
        cpu.tick();
    }

    public AddressSpace getMemory() {
        return mmu;
    }

    public Cpu getCpu() {
        return cpu;
    }

}