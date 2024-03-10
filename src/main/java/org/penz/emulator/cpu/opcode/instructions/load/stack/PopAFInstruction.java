package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Pop the value at the top of the stack into register A and flags
 */
@SuppressWarnings("unused")
public class PopAFInstruction extends OpCode {

    public PopAFInstruction() {
        super(0xF1, "POP AF", 12);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.getFlags().setFlags(addressSpace.readByte(registers.getSP()));
        registers.incrementSP();
        registers.setA(addressSpace.readByte(registers.getSP()));
        registers.incrementSP();
        return cycles;
    }
}
