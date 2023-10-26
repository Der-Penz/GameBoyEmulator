package org.penz.emulator.cpu.opcode.instructions.load.e;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register D into register E
 */
@SuppressWarnings("unused")
public class LoadEDInstruction extends OpCode {

    public LoadEDInstruction() {
        super(0x5A, "LD E, D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(registers.getD());
        return cycles;
    }
}
