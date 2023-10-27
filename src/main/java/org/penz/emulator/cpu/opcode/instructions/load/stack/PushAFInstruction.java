package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Push the value from register A and flags onto the stack
 */
@SuppressWarnings("unused")
public class PushAFInstruction extends OpCode {

    public PushAFInstruction() {
        super(0xF5, "PUSH AF", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.decrementSP();
        addressSpace.writeByte(registers.getSP(), registers.getA());
        registers.decrementSP();
        addressSpace.writeByte(registers.getSP(), registers.getFlags().getFlags());
        return cycles;
    }
}
