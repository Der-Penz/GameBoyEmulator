package org.penz.emulator.graphics;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.memory.AddressSpace;

public class LcdRegister {

    private int lY;

    private int lYC;

    private final StatRegister sTAT;

    public LcdRegister(InterruptManager interruptManager) {
        this.sTAT = new StatRegister(interruptManager);
    }

    public void incrementLYC() {
        this.lY++;
        if (lY > 153) {
            this.lY = 0;
        }
        sTAT.setLYLYCEquals(this.lY == lYC);
    }

    public void setlYC(int lYC) {
        this.lYC = lYC;
        sTAT.setLYLYCEquals(lY == lYC);
    }

    public int getLY() {
        return lY;
    }

    public int getLYC() {
        return lYC;
    }

    public void setLYC(int lYC) {
        this.lYC = lYC;
        sTAT.setLYLYCEquals(lY == lYC);
    }


}
