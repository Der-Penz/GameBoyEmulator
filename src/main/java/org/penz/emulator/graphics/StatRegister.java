package org.penz.emulator.graphics;

import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.cpu.interrupt.InterruptType;
import org.penz.emulator.graphics.enums.LCDInterruptMode;
import org.penz.emulator.graphics.enums.PpuMode;

public class StatRegister {

    private PpuMode ppuMode;

    private boolean lYLYCEquals;

    private boolean modeLYCInt;
    private boolean mode0Int;
    private boolean mode1Int;
    private boolean mode2Int;

    private final InterruptManager interruptManager;

    public StatRegister(InterruptManager interruptManager) {
        this.interruptManager = interruptManager;
    }

    /**
     * Will request the LCD interrupt if the interrupt mode is enabled
     *
     * @param interruptMode the interrupt mode to check
     */
    public void tryRequestInterrupt(LCDInterruptMode interruptMode) {
        //TODO: research STAT blocking
        switch (interruptMode) {
            case LYC:
                if (modeLYCInt) {
                    interruptManager.requestInterrupt(InterruptType.LCD);
                }
                break;
            case MODE1:
                if (mode1Int) {
                    interruptManager.requestInterrupt(InterruptType.LCD);
                }
                break;
            case MODE2:
                if (mode2Int) {
                    interruptManager.requestInterrupt(InterruptType.LCD);
                }
                break;
            case MODE0:
                if (mode0Int) {
                    interruptManager.requestInterrupt(InterruptType.LCD);
                }
                break;
        }
    }

    public void setPpuMode(PpuMode ppuMode) {
        this.ppuMode = ppuMode;
    }

    public void setLYLYCEquals(boolean lYLYCEquals) {
        this.lYLYCEquals = lYLYCEquals;

        if (lYLYCEquals) {
            tryRequestInterrupt(LCDInterruptMode.LYC);
        }
    }

    public void setModeLYCInt(boolean modeLYCInt) {
        this.modeLYCInt = modeLYCInt;
    }

    public void setMode0Int(boolean mode0Int) {
        this.mode0Int = mode0Int;
    }

    public void setMode1Int(boolean mode1Int) {
        this.mode1Int = mode1Int;
    }

    public void setMode2Int(boolean mode2Int) {
        this.mode2Int = mode2Int;
    }

    public int getSTATRegister() {
        int statReg = ppuMode.getBitRepresentation();
        statReg |= ((lYLYCEquals ? 1 : 0) << 2);
        statReg |= ((mode0Int ? 1 : 0) << 3);
        statReg |= ((mode1Int ? 1 : 0) << 4);
        statReg |= ((mode2Int ? 1 : 0) << 5);
        statReg |= ((modeLYCInt ? 1 : 0) << 6);
        statReg |= 0b10000000;

        return statReg;
    }

    public void setSTATRegister(int value) {
        setLYLYCEquals((value & 0x04) != 0);
        setMode0Int((value & 0x08) != 0);
        setMode1Int((value & 0x10) != 0);
        setMode2Int((value & 0x20) != 0);
        setModeLYCInt((value & 0x40) != 0);
    }

    public boolean getLYLYCEquals() {
        return lYLYCEquals;
    }

    public PpuMode getPpuMode() {
        return ppuMode;
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

    public boolean isModeLYCInt() {
        return modeLYCInt;
    }
}
