package org.penz.emulator.cpu.instructions;

import org.penz.emulator.cpu.Registers;

public abstract class OpCode {

    /**
     * The opcode value
     *
     * @example 0x00 for NOP
     */
    protected final int opcode;

    /**
     * The opcode name
     *
     * @example NOP
     */
    protected final String name;

    protected final int cycles;

    public OpCode(int opcode, String name, int cycles) {
        this.opcode = opcode;
        this.name = name;
        this.cycles = cycles;
    }

    /**
     * Execute the opcode
     *
     * @param registers The CPU registers and flags
     * @return The number of cycles the opcode took to execute
     */
    abstract int execute(Registers registers);

    @Override
    public String toString() {
        return String.format("OpCode: %s-%02X-: %d", this.name, this.opcode, this.cycles);
    }

    public int getOpcode() {
        return opcode;
    }

    public String getName() {
        return name;
    }

    public int getCycles() {
        return cycles;
    }
}
