package org.penz.emulator.cpu.opcode.instructions.load.b;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register B into register B
 */
@SuppressWarnings("unused")
public class LoadBBInstruction extends OpCode {

    public LoadBBInstruction() {
        super(0x40, "LD B, B", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(registers.getB());
        return cycles;
    }
}
