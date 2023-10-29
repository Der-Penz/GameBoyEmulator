package org.penz.emulator.cpu.opcode.instructions.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Game boy switches to low power mode until an interrupt occurs.
 * TODO: might need an implementation
 */
public class HaltInstruction extends OpCode {
    public HaltInstruction() {
        super(0x76, "HALT", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        return cycles;
    }
}
