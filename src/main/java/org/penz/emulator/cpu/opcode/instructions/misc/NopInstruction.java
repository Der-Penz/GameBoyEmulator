package org.penz.emulator.cpu.opcode.instructions.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * No operation instruction
 * CPU idles for 4 cycles
 */
public class NopInstruction extends OpCode {

    private int cycles;

    public NopInstruction() {
        super(0x00, "NOP", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        return cycles;
    }
}

