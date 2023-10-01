package org.penz.emulator.cpu.instructions;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.memory.AddressSpace;

public class NopInstruction extends OpCode {

    private int cycles;

    public NopInstruction() {
        super(0x00, "NOP", 4, new DataType[]{});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        return cycles;
    }
}

