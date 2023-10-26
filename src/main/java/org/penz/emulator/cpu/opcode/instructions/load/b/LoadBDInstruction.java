package org.penz.emulator.cpu.opcode.instructions.load.b;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register D into register B
 */
@SuppressWarnings("unused")
public class LoadBDInstruction extends OpCode {

    public LoadBDInstruction() {
        super(0x42, "LD B, D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(registers.getD());
        return cycles;
    }
}
