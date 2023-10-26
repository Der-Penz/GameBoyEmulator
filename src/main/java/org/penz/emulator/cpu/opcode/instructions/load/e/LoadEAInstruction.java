package org.penz.emulator.cpu.opcode.instructions.load.e;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register A into register E
 */
@SuppressWarnings("unused")
public class LoadEAInstruction extends OpCode {

    public LoadEAInstruction() {
        super(0x5F, "LD E, A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(registers.getA());
        return cycles;
    }
}
