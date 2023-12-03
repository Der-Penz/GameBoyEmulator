package org.penz.emulator.graphics;

import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;

public class Ppu implements AddressSpace {


    private final static int CYCLES_PER_SCANLINE = 456;

    private int scanlineCounter = Ppu.CYCLES_PER_SCANLINE;

    private LcdControl lcdControl;
    private final LcdRegister lcdRegister;

    private final AddressSpace vRam;

    private final InterruptManager interruptManager;

    public Ppu(InterruptManager interruptManager) {
        this.interruptManager = interruptManager;
        this.vRam = new Ram(0x8000, 0x9FFF);
        this.lcdRegister = new LcdRegister(interruptManager);
        this.lcdControl = new LcdControl();
    }


    public void tick(int passedCycles) {
        if (!lcdControl.isLcdEnabled()) {
            return;
        }


//        scanlineCounter -= passedCycles;

//        if(scanlineCounter <= 0){
//            scanlineCounter += Ppu.CYCLES_PER_SCANLINE;
//            lcdRegister.incrementLYC();
//
//            int currentLine = lcdRegister.getLY();
//            if(currentLine == 144) {
//                interruptManager.requestInterrupt(InterruptType.VBLANK);
//                lcdRegister.getSTAT().setPpuMode(PpuMode.V_BLANK);
//            }else if(currentLine < 144) {
//                //render line
//            }
//        }
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
        if (vRam.accepts(address)) {
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
            return vRam.readByte(address);
        }

        return 0;
    }
}
