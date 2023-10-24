package org.penz.emulator.cpu.opcode.instructions.load.b;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register A into register B
 */
public class LoadBAInstruction extends OpCode {

    public LoadBAInstruction() {
        super(0x32, "LD B, A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(registers.getA());
        return cycles;
    }
}
