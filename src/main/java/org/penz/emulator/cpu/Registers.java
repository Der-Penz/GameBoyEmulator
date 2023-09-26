package org.penz.emulator.cpu;

import org.penz.emulator.Constants;

public class Registers {

    /**
     * 8 Bit registers
     * A - Accumulator
     * B - General purpose
     * C - General purpose
     * D - General purpose
     * E - General purpose
     * H - General purpose
     * L - General purpose
     */
    private byte a, b, c, d, e, h, l;

    /**
     * 8 Bit flags register
     */
    private final Flags flags = new Flags();
    private short sp;
    private short pc;

    public Registers() {
        this.pc = 0x0000;

    }

    public void incrementPC() {
        pc = (short) ((pc + 1) & Constants.BIT_16_MAX_VALUE);
    }

    public void decrementPC() {
        pc = (short) ((pc - 1) & Constants.BIT_16_MAX_VALUE);
    }

    public void incrementSP() {
        sp = (short) ((sp + 1) & Constants.BIT_16_MAX_VALUE);
    }

    public void decrementSP() {
        sp = (short) ((sp - 1) & Constants.BIT_16_MAX_VALUE);
    }

    public byte getA() {
        return a;
    }

    public void setA(byte a) {
        this.a = a;
    }

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public byte getC() {
        return c;
    }

    public void setC(byte c) {
        this.c = c;
    }

    public byte getD() {
        return d;
    }

    public void setD(byte d) {
        this.d = d;
    }

    public byte getE() {
        return e;
    }

    public void setE(byte e) {
        this.e = e;
    }

    public byte getH() {
        return h;
    }

    public void setH(byte h) {
        this.h = h;
    }

    public byte getL() {
        return l;
    }

    public void setL(byte l) {
        this.l = l;
    }

    public short getSp() {
        return sp;
    }

    public void setSp(short sp) {
        this.sp = sp;
    }

    public short getPc() {
        return pc;
    }

    public void setPc(short pc) {
        this.pc = pc;
    }
}
