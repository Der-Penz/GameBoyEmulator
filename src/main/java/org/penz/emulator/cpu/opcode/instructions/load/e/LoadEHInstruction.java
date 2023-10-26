package org.penz.emulator.cpu.opcode.instructions.load.e;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register H into register E
 */
@SuppressWarnings("unused")
public class LoadEHInstruction extends OpCode {

    public LoadEHInstruction() {
        super(0x5C, "LD E, H", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(registers.getH());
        return cycles;
    }
}
