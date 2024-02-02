package org.penz.emulator.graphics;

import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.cpu.interrupt.InterruptType;
import org.penz.emulator.graphics.enums.LCDInterruptMode;
import org.penz.emulator.graphics.enums.PpuMode;
import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;

public class Ppu implements AddressSpace {


    private final static int CYCLES_PER_SCANLINE = 456;
    private final LcdRegister lcdRegister;
    private final Ram vRam;
    private final InterruptManager interruptManager;
    private final LcdControl lcdControl;
    private final PixelFIFO pixelFIFO;
    private final Oam oam;

    private final Display display;
    private int scanlineCounter = Ppu.CYCLES_PER_SCANLINE;

    public Ppu(InterruptManager interruptManager, AddressSpace mmu, Display display) {
        this.oam = new Oam(mmu);
        this.display = display;
        this.interruptManager = interruptManager;
        this.vRam = new Ram(0x8000, 0x9FFF);
        this.lcdRegister = new LcdRegister(interruptManager);
        this.lcdControl = new LcdControl();
        PixelFetcher pixelFetcher = new PixelFetcher(vRam, lcdControl, lcdRegister);
        this.pixelFIFO = new PixelFIFO(display, pixelFetcher, lcdControl, lcdRegister);
    }

    public void tick(int passedCycles) {
        if (!lcdControl.isLcdEnabled()) {
            scanlineCounter = 0;
            lcdRegister.setLY(0);
            lcdRegister.getSTAT().setPpuMode(PpuMode.V_BLANK);
            return;
        }

        scanlineCounter += passedCycles;


        if (scanlineCounter >= Ppu.CYCLES_PER_SCANLINE) {
            scanlineCounter -= Ppu.CYCLES_PER_SCANLINE;
            lcdRegister.incrementLYC(); //wrap around to 0 if 153
        }
        int currentLine = lcdRegister.getLY();

        switch (lcdRegister.getSTAT().getPpuMode()) {
            case H_BLANK:
                //TODO implement H_BLANK wait

                if (currentLine == 143) {
                    changeMode(PpuMode.V_BLANK);
                } else {
                    changeMode(PpuMode.OAM_SCAN);
                }
                break;
            case V_BLANK:
                //TODO implement V_BLANK wait
                if (currentLine == 0) {
                    changeMode(PpuMode.OAM_SCAN);
                }
                break;
            case OAM_SCAN:
                //TODO: implement OAM scan

                if (scanlineCounter >= 70) {
                    changeMode(PpuMode.PIXEL_TRANSFER);
                }
                break;
            case PIXEL_TRANSFER:
                for (int i = 0; i < passedCycles; i++) {
                    pixelFIFO.tick();
                    if (pixelFIFO.getX() == 160) {
                        changeMode(PpuMode.H_BLANK);
                        break;
                    }
                }

        }
    }

    /**
     * Change the current PPU mode and request interrupts if necessary
     *
     * @param nextMode The next mode to change to
     */
    private void changeMode(PpuMode nextMode) {
        lcdRegister.getSTAT().setPpuMode(nextMode);

        switch (nextMode) {
            case H_BLANK:
                lcdRegister.getSTAT().tryRequestInterrupt(LCDInterruptMode.MODE0);
                break;
            case V_BLANK:
                interruptManager.requestInterrupt(InterruptType.VBLANK);
                display.onFrameReady();
                lcdRegister.getSTAT().tryRequestInterrupt(LCDInterruptMode.MODE1);
                break;
            case OAM_SCAN:
                lcdRegister.getSTAT().tryRequestInterrupt(LCDInterruptMode.MODE2);
                break;
            case PIXEL_TRANSFER:
                pixelFIFO.reset();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean accepts(int address) {
        if (vRam.accepts(address) || oam.accepts(address) || pixelFIFO.accepts(address)) {
            return true;
        }

        return address == 0xFF40 || address == 0xFF41 || address == 0xFF44 || address == 0xFF45 || address == 0xFE46;
    }

    @Override
    public void writeByte(int address, int value) {
        if (vRam.accepts(address) && lcdRegister.getSTAT().getPpuMode() != PpuMode.PIXEL_TRANSFER) {
            vRam.writeByte(address, value);
            return;
        }

        if (oam.accepts(address)
                && lcdRegister.getSTAT().getPpuMode() != PpuMode.PIXEL_TRANSFER
                && lcdRegister.getSTAT().getPpuMode() != PpuMode.OAM_SCAN) {
            oam.writeByte(address, value);
            return;
        }

        if (address == 0xFF40) {
            lcdControl.setLcdControlRegister(value);
            return;
        }
        if (address == 0xFF41) {
            lcdRegister.getSTAT().getSTATRegister();
            return;
        }
        if (address == 0xFF44) {
            throw new IllegalArgumentException("LY is read only");
        }

        if (address == 0xFF45) {
            lcdRegister.setLYC(value);
            return;
        }
        if (address == 0xFE46) {
            //TODO might need to pass 160 cycles here for better timing
            oam.doDMATransfer(value);
            return;
        }

        if (pixelFIFO.accepts(address)) {
            pixelFIFO.writeByte(address, value);
            return;
        }
    }

    @Override
    public int readByte(int address) {
        if (address == 0xFF40) {
            return lcdControl.getLcdControlRegister();
        } else if (address == 0xFF41) {
            return lcdRegister.getSTAT().getSTATRegister();
        } else if (address == 0xFF44) {
            return lcdRegister.getLY();
        } else if (address == 0xFF45) {
            return lcdRegister.getLYC();
        }

        if (vRam.accepts(address)) {
            if (lcdRegister.getSTAT().getPpuMode() == PpuMode.PIXEL_TRANSFER) {
                return 0xFF;
            }
            return vRam.readByte(address);
        }

        if (oam.accepts(address)) {
            return oam.readByte(address);
        }

        if (pixelFIFO.accepts(address)) {
            return pixelFIFO.readByte(address);
        }

        return 0;
    }
}
