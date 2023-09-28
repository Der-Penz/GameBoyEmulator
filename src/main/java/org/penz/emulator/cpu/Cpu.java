package org.penz.emulator.cpu;

public class Cpu {

    /**
     * Cpu Registers and Flags
     */
    private final Registers registers;

    public Cpu(){
        this.registers = new Registers();
    }

    public Registers getRegisters() {
        return registers;
    }
}
