package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Push the value from register BC onto the stack
 */
@SuppressWarnings("unused")
public class PushBCInstruction extends OpCode {

    public PushBCInstruction() {
        super(0xC5, "PUSH BC", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.decrementSP();
        addressSpace.writeByte(registers.getSP(), registers.getB());
        registers.decrementSP();
        addressSpace.writeByte(registers.getSP(), registers.getC());
        return cycles;
    }
}
