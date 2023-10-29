package org.penz.emulator.cpu.opcode.instructions.arithmetic.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Complement Accumulator
 */
@SuppressWarnings("unused")
public class CPLInstruction extends OpCode {
    public CPLInstruction() {
        super(0x2F, "CPL", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(~registers.getA());

        registers.getFlags().setH(true);
        registers.getFlags().setN(true);

        return cycles;
    }
}
