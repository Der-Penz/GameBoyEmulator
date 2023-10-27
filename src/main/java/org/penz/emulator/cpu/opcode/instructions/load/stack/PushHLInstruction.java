package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Push the value from register HL onto the stack
 */
@SuppressWarnings("unused")
public class PushHLInstruction extends OpCode {

    public PushHLInstruction() {
        super(0xE5, "PUSH HL", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.decrementSP();
        addressSpace.writeByte(registers.getSP(), registers.getH());
        registers.decrementSP();
        addressSpace.writeByte(registers.getSP(), registers.getL());
        return cycles;
    }
}
