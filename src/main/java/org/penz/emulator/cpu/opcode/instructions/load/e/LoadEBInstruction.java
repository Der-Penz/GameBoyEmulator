package org.penz.emulator.cpu.opcode.instructions.load.e;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register B into register E
 */
@SuppressWarnings("unused")
public class LoadEBInstruction extends OpCode {

    public LoadEBInstruction() {
        super(0x58, "LD E, B", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(registers.getB());
        return cycles;
    }
}
