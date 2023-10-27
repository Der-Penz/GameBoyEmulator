package org.penz.emulator.cpu.opcode.instructions.load.h;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register E into register H
 */
@SuppressWarnings("unused")
public class LoadHEInstruction extends OpCode {

    public LoadHEInstruction() {
        super(0x63, "LD H, E", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(registers.getE());
        return cycles;
    }
}
