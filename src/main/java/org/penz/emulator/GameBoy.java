package org.penz.emulator;

import org.penz.emulator.cpu.Cpu;
import org.penz.emulator.cpu.Timer;
import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.input.ButtonController;
import org.penz.emulator.input.Joypad;
import org.penz.emulator.memory.*;
import org.penz.emulator.memory.cartridge.CGBFlag;
import org.penz.emulator.memory.cartridge.Cartridge;

import java.io.IOException;

public class GameBoy {

    public static final int CLOCK_SPEED = 4194304;
    private final Cpu cpu;

    private final Mmu mmu;

    private final Timer timer;

    private Cartridge cartridge;

    public GameBoy(String romPath, ButtonController controls) throws IOException {

        cartridge = Cartridge.loadCartridge(romPath);

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
        this.mmu.addMemoryBank(new Ram(0xFF80, 0xFFFE)); //High ram
        this.mmu.indexBanks();
        this.cpu = new Cpu(mmu, interruptManager);
    }

    public static void main(String[] args) throws IOException {
        var romPath = "src/main/resources/roms/tetris.gb";

        var gameBoy = new GameBoy(romPath, null);
        gameBoy.start();
    }

    public void start() {
        while (true) {
            System.out.println("game boy clock tick");
            tick();
        }
    }

    public void tick() {
        int passedCycles = cpu.tick();
        timer.tick(passedCycles);
    }

    public AddressSpace getMemory() {
        return mmu;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public Cartridge getCartridge() {
        return cartridge;
    }
}