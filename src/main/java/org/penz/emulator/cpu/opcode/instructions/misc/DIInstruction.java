package org.penz.emulator.cpu.opcode.instructions.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Disable interrupts
 */
public class DIInstruction extends OpCode {
    public DIInstruction() {
        super(0xF3, "DI", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        //TODO: implement
        return cycles;
    }
}
