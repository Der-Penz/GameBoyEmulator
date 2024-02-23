package org.penz.emulator;

import org.penz.emulator.cpu.Cpu;
import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.cpu.timer.Timer;
import org.penz.emulator.graphics.IDisplay;
import org.penz.emulator.graphics.Ppu;
import org.penz.emulator.gui.SimpleDisplay;
import org.penz.emulator.input.IButtonController;
import org.penz.emulator.input.Joypad;
import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.EchoAddressSpace;
import org.penz.emulator.memory.Mmu;
import org.penz.emulator.memory.Ram;
import org.penz.emulator.memory.cartridge.CGBFlag;
import org.penz.emulator.memory.cartridge.Cartridge;

import java.io.IOException;

public class GameBoy {

    public static final int CLOCK_SPEED = 4194304;
    public static final int FPS = 60;
    private final Cpu cpu;

    private final Mmu mmu;

    private final Ppu ppu;

    private final Timer timer;
    private final IDisplay display;
    private Cartridge cartridge;
    private boolean paused = false;

    public GameBoy(String romPath, IButtonController controls, IDisplay display) throws IOException {

        cartridge = Cartridge.loadCartridge(romPath);

        if (cartridge.isColorGameBoy() == CGBFlag.CGB_ONLY) {
            throw new UnsupportedOperationException("GameBoy Color is not supported yet");
        }

        InterruptManager interruptManager = new InterruptManager();
        this.timer = new Timer(interruptManager);
        Ram ram = new Ram(0xC000, 0xDFFF);

        this.display = display;
        this.mmu = new Mmu();
        this.ppu = new Ppu(interruptManager, mmu, display);

        this.mmu.addMemoryBank(cartridge);
        this.mmu.addMemoryBank(ram);
        this.mmu.addMemoryBank(new EchoAddressSpace(ram, 0xC000, 0xE000, 0xFDFF));
        this.mmu.addMemoryBank(new Joypad(interruptManager, controls));
        this.mmu.addMemoryBank(interruptManager);
        this.mmu.addMemoryBank(this.timer);
        this.mmu.addMemoryBank(ppu);
        this.mmu.addMemoryBank(new Ram(0xFF80, 0xFFFE)); //High ram
        this.mmu.indexBanks();
        this.cpu = new Cpu(mmu, interruptManager);
    }

    public static void main(String[] args) throws IOException {
        var romPath = "src/main/resources/roms/tetris.gb";

        var gameBoy = new GameBoy(romPath, null, new SimpleDisplay());
        gameBoy.run(true);
    }

    /**
     * Runs the Game Boy as long as the pause flag is not set
     */
    public void run(boolean fastMode) {
        paused = false;
        while (!paused) {
            frame();
            if (!fastMode) {

                try {
                    int frameExecutionDuration = 3;
                    Thread.sleep((1000 / GameBoy.FPS) - frameExecutionDuration);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Executes one Machine cycle
     *
     * @return true if a new frame is ready to be drawn
     */
    public boolean tick() {
        int passedCycles = cpu.tick();
        timer.tick(passedCycles);
        return ppu.tick(passedCycles);
    }

    /**
     * Executes one frame
     */
    public void frame() {
        boolean frameReady = false;
        while (!frameReady) {
            frameReady = tick();
        }
        display.onFrameReady();
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

    public void pause() {
        paused = true;
    }

    public boolean isRunning() {
        return !paused;
    }
}