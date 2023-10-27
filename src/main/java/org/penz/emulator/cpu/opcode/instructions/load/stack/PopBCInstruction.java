package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Pop the value at the top of the stack into BC
 */
@SuppressWarnings("unused")
public class PopBCInstruction extends OpCode {

    public PopBCInstruction() {
        super(0xC1, "POP BC", 12);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(addressSpace.readByte(registers.getSP()));
        registers.incrementSP();
        registers.setB(addressSpace.readByte(registers.getSP()));
        registers.incrementSP();
        return cycles;
    }
}
