package org.penz.emulator.cpu.opcode.instructions.load.h;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register H into register H
 */
@SuppressWarnings("unused")
public class LoadHHInstruction extends OpCode {

    public LoadHHInstruction() {
        super(0x64, "LD H, H", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(registers.getH());
        return cycles;
    }
}
