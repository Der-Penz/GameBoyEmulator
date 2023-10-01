package org.penz.emulator.cpu.instructions;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.memory.AddressSpace;

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

    /**
     * The number of cycles the opcode takes to execute
     */
    protected final int cycles;

    /**
     * The arguments type the opcode takes and how many
     */
    protected final DataType[] argsType;

    public OpCode(int opcode, String name, int cycles, DataType[] argsType) {
        this.opcode = opcode;
        this.name = name;
        this.cycles = cycles;
        this.argsType = argsType;
    }

    /**
     * Execute the opcode
     *
     * @param registers The CPU registers and flags
     * @return The number of cycles the opcode took to execute
     */
    public abstract int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args);

    @Override
    public String toString() {
        return String.format("OpCode: %s 0x%02X: %d", this.name, this.opcode, this.cycles);
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

    public int getArgsTypeLength() {
        return argsType.length;
    }

    public DataType[] getArgsType() {
        return argsType;
    }
}
