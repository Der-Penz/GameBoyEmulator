package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Pop the value at the top of the stack into DE
 */
@SuppressWarnings("unused")
public class PopDEInstruction extends OpCode {

    public PopDEInstruction() {
        super(0xD1, "POP DE", 12);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(addressSpace.readByte(registers.getSP()));
        registers.incrementSP();
        registers.setD(addressSpace.readByte(registers.getSP()));
        registers.incrementSP();
        return cycles;
    }
}
