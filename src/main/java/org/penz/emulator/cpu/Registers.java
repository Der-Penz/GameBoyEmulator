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

    /**
     * Interrupt Master Enable. Controls whether any interrupt can be triggered or not.
     * If this is disabled, no interrupt can be triggered, even if the interrupt is enabled in the Interrupt Enable Register.
     * Cannot be read by the Cartridge in any way, and is modified by these instructions/events only: ei, di, reti
     */
    private boolean ime;

    public Registers() {
        this.pc = 0x0000;
        ime = false;
    }

    /**
     * Enable IME. Called by the EI instruction.
     */
    public void enableInterrupts() {
        ime = true;
    }

    /**
     * Disable IME. Called by the DI instruction.
     */
    public void disableInterrupts() {
        ime = false;
    }

    /**
     * Check if interrupts are enabled.
     */
    public boolean interruptsEnabled() {
        return ime;
    }

    /**
     * Get the current program counter value and increment it
     * @return current program counter value
     */
    public int getAndIncPC() {
        int temp = pc;
        pc = (pc + 1) & Constants.WORD_MAX_VALUE;
        return temp;
    }

    /**
     * Increment the program counter and return the new value
     *
     * @return the new program counter value after incrementing
     */
    public int incrementPC() {
        pc = ((pc + 1) & Constants.WORD_MAX_VALUE);
        return pc;
    }

    /**
     * Decrement the program counter and return the new value
     *
     * @return the new program counter value after decrementing
     */
    public int decrementPC() {
        pc = ((pc - 1) & Constants.WORD_MAX_VALUE);
        return pc;
    }

    /**
     * Increment the stack pointer and return the new value
     *
     * @return the new stack pointer value after incrementing
     */
    public int incrementSP() {
        sp = ((sp + 1) & Constants.WORD_MAX_VALUE);
        return sp;
    }

    /**
     * Decrement the stack pointer and return the new value
     *
     * @return the new stack pointer value after decrementing
     */
    public int decrementSP() {
        sp = ((sp - 1) & Constants.WORD_MAX_VALUE);
        return sp;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a & Constants.BYTE_MAX_VALUE;
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

    /**
     * Return the stack pointer with an offset, but ensure it is within the bounds of the address space
     *
     * @param offset the offset to add to the stack pointer positive or negative
     * @return the stack pointer with the offset applied
     */
    public int getSPSafe(int offset) {
        return (sp + offset) & Constants.WORD_MAX_VALUE;
    }

    public void setSP(int sp) {
        this.sp = sp & Constants.WORD_MAX_VALUE;
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int pc) {
        this.pc = pc & Constants.WORD_MAX_VALUE;
    }

    public Flags getFlags() {
        return flags;
    }

    public int getAF() {
        return BitUtil.toWord(a, flags.getFlags());
    }

    public void setAF(int value) {
        a = BitUtil.shiftRight(value, 1) & Constants.BYTE_MAX_VALUE;
        flags.setFlags(value & Constants.BYTE_MAX_VALUE);
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
