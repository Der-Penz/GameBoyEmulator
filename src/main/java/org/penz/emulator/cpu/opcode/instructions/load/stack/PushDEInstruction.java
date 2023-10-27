package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Push the value from register DE onto the stack
 */
@SuppressWarnings("unused")
public class PushDEInstruction extends OpCode {

    public PushDEInstruction() {
        super(0xD5, "PUSH DE", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.decrementSP();
        addressSpace.writeByte(registers.getSP(), registers.getD());
        registers.decrementSP();
        addressSpace.writeByte(registers.getSP(), registers.getE());
        return cycles;
    }
}
