package org.penz.emulator.cpu.opcode.instructions.load.h;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register C into register H
 */
@SuppressWarnings("unused")
public class LoadHCInstruction extends OpCode {

    public LoadHCInstruction() {
        super(0x61, "LD H, C", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(registers.getC());
        return cycles;
    }
}
