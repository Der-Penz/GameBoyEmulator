package org.penz.emulator.cpu.opcode.instructions.load.d;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register B into register D
 */
public class LoadDBInstruction extends OpCode {

    public LoadDBInstruction() {
        super(0x50, "LD D, B", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(registers.getB());
        return cycles;
    }
}
