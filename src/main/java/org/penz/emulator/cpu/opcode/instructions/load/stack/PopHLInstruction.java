package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Pop the value at the top of the stack into HL
 */
@SuppressWarnings("unused")
public class PopHLInstruction extends OpCode {

    public PopHLInstruction() {
        super(0xE1, "POP HL", 12);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(addressSpace.readByte(registers.getSP()));
        registers.incrementSP();
        registers.setH(addressSpace.readByte(registers.getSP()));
        registers.incrementSP();
        return cycles;
    }
}
