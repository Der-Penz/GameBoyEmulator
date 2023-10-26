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
    private int a, b, c, d, e, h, l;

    /**
     * 8 Bit flags register
     */
    private final Flags flags = new Flags();
    private int sp;
    private int pc;

    public Registers() {
        this.pc = 0x0000;

    }

    /**
     * Get the current program counter value and increment it
     * @return current program counter value
     */
    public int getAndIncPC() {
        return pc++;
    }

    public void incrementPC() {
        pc = ((pc + 1) & Constants.WORD_MAX_VALUE);
    }

    public void decrementPC() {
        pc = ((pc - 1) & Constants.WORD_MAX_VALUE);
    }

    public void incrementSP() {
        sp = ((sp + 1) & Constants.WORD_MAX_VALUE);
    }

    public void decrementSP() {
        sp = ((sp - 1) & Constants.WORD_MAX_VALUE);
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getSP() {
        return sp;
    }

    public void setSP(int sp) {
        this.sp = sp;
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int pc) {
        this.pc = pc;
    }

    public Flags getFlags() {
        return flags;
    }

    public int getAF() {
        return BitUtil.toWord(a, flags.getFlags());
    }

    public void setAF(int value) {
        a = BitUtil.shiftRight(value, 1) & Constants.BYTE_MAX_VALUE;
        flags.setFlags((byte) (value & Constants.BYTE_MAX_VALUE));
    }

    public int getBC() {
        return BitUtil.toWord(b, c);
    }

    public void setBC(int value) {
        b = BitUtil.shiftRight(value, 1) & Constants.BYTE_MAX_VALUE;
        c = value & Constants.BYTE_MAX_VALUE;
    }

    public int getDE() {
        return BitUtil.toWord(d, e);
    }

    public void setDE(int value) {
        d = BitUtil.shiftRight(value, 1) & Constants.BYTE_MAX_VALUE;
        e = value & Constants.BYTE_MAX_VALUE;
    }

    public int getHL() {
        return BitUtil.toWord(h, l);
    }

    public void setHL(int value) {
        h = BitUtil.shiftRight(value, 1) & Constants.BYTE_MAX_VALUE;
        l = value & Constants.BYTE_MAX_VALUE;
    }

}
