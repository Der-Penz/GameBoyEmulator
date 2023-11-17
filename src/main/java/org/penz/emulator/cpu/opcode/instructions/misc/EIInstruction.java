package org.penz.emulator.cpu.opcode.instructions.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Enable interrupts
 */
@SuppressWarnings("unused")
public class EIInstruction extends OpCode {
    public EIInstruction() {
        super(0xFB, "EI", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.enableInterrupts();
        return cycles;
    }
}
