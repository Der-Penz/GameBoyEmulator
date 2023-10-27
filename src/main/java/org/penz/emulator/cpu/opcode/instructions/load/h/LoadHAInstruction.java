package org.penz.emulator.cpu.opcode.instructions.load.h;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register A into register H
 */
@SuppressWarnings("unused")
public class LoadHAInstruction extends OpCode {

    public LoadHAInstruction() {
        super(0x67, "LD H, A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(registers.getA());
        return cycles;
    }
}
