package org.penz.emulator.cpu.opcode.instructions.load.d;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register A into register D
 */
@SuppressWarnings("unused")
public class LoadDAInstruction extends OpCode {

    public LoadDAInstruction() {
        super(0x57, "LD D, A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(registers.getA());
        return cycles;
    }
}
