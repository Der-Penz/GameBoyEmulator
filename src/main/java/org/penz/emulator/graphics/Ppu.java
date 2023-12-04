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
    private final AddressSpace vRam;
    private final InterruptManager interruptManager;
    private int scanlineCounter = Ppu.CYCLES_PER_SCANLINE;
    private final LcdControl lcdControl;

    public Ppu(InterruptManager interruptManager) {
        this.interruptManager = interruptManager;
        this.vRam = new Ram(0x8000, 0x9FFF);
        this.lcdRegister = new LcdRegister(interruptManager);
        this.lcdControl = new LcdControl();
    }

    public void tick(int passedCycles) {
        if (!lcdControl.isLcdEnabled()) {
            scanlineCounter = Ppu.CYCLES_PER_SCANLINE;
            lcdRegister.setLY(0);
            lcdRegister.getSTAT().setPpuMode(PpuMode.V_BLANK);
            return;
        }

        scanlineCounter -= passedCycles;

        if (scanlineCounter <= 0) {
            scanlineCounter += Ppu.CYCLES_PER_SCANLINE;
            lcdRegister.incrementLYC(); //wrap around to 0 if 153
        }
        int currentLine = lcdRegister.getLY();

        switch (lcdRegister.getSTAT().getPpuMode()) {
            case H_BLANK:
                //TODO implement H_BLANK wait


                if (currentLine == 144) {
                    interruptManager.requestInterrupt(InterruptType.VBLANK);
                    lcdRegister.getSTAT().setPpuMode(PpuMode.V_BLANK);
                    lcdRegister.getSTAT().tryRequestInterrupt(LCDInterruptMode.MODE1);
                } else {
                    lcdRegister.getSTAT().setPpuMode(PpuMode.OAM_SCAN);
                    lcdRegister.getSTAT().tryRequestInterrupt(LCDInterruptMode.MODE2);
                }
                break;
            case V_BLANK:
                //TODO implement V_BLANK wait
                if (currentLine == 0) {
                    lcdRegister.getSTAT().setPpuMode(PpuMode.OAM_SCAN);
                    lcdRegister.getSTAT().tryRequestInterrupt(LCDInterruptMode.MODE2);
                }
                break;
            case OAM_SCAN:
                //TODO: implement OAM scan

                lcdRegister.getSTAT().setPpuMode(PpuMode.PIXEL_TRANSFER);
                break;
            case PIXEL_TRANSFER:
                //TODO: implement pixel transfer

                lcdRegister.getSTAT().setPpuMode(PpuMode.H_BLANK);
                lcdRegister.getSTAT().tryRequestInterrupt(LCDInterruptMode.MODE0);
        }
    }

    @Override
    public boolean accepts(int address) {
        if (vRam.accepts(address)) {
            return true;
        }

        return address == 0xFF40 || address == 0xFF41 || address == 0xFF44 || address == 0xFF45;
    }

    @Override
    public void writeByte(int address, int value) {
        if (vRam.accepts(address) && lcdRegister.getSTAT().getPpuMode() != PpuMode.PIXEL_TRANSFER) {
            vRam.writeByte(address, value);
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
        } else if (address == 0xFF45) {
            lcdRegister.setLYC(value);
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

        return 0;
    }
}
