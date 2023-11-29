package org.penz.emulator.graphics;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.cpu.interrupt.InterruptType;

public class StatRegister {

    private PpuMode ppuMode;

    private int statIntMode = 0x00;

    private boolean lYLYCEquals;

    private boolean mode0Int;
    private boolean mode1Int;
    private boolean mode2Int;

    private InterruptManager interruptManager;

    public StatRegister(InterruptManager interruptManager) {
        this.interruptManager = interruptManager;
    }

    public void setPpuMode(PpuMode ppuMode) {
        this.ppuMode = ppuMode;
    }

    public void setLYLYCEquals(boolean lYLYCEquals) {
        this.lYLYCEquals = lYLYCEquals;

        if(lYLYCEquals && BitUtil.getBit(statIntMode, 6)){
            interruptManager.requestInterrupt(InterruptType.LCD);
        }
    }

    public void setStatIntMode(int bitPos){
        statIntMode = BitUtil.setBit(statIntMode, bitPos);
    }

    public int getSTAT() {
        int statReg = ppuMode.ordinal();
        statReg |= lYLYCEquals ? 0b100 : 0;
        statReg |= statIntMode << 4;

        return statReg;
    }

    public boolean isMode0Int() {
        return mode0Int;
    }

    public boolean isMode1Int() {
        return mode1Int;
    }

    public boolean isMode2Int() {
        return mode2Int;
    }
}
