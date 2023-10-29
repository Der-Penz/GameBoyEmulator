package org.penz.emulator.cpu.opcode.instructions.arithmetic.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set Carry Flag
 */
@SuppressWarnings("unused")
public class SCFInstruction extends OpCode {
    public SCFInstruction() {
        super(0x37, "SCF", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.getFlags().setC(true);
        registers.getFlags().setH(false);
        registers.getFlags().setN(false);

        return cycles;
    }
}
