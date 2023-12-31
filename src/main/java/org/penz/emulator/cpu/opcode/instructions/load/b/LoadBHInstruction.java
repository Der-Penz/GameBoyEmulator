package org.penz.emulator.cpu.opcode.instructions.load.b;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register H into register B
 */
@SuppressWarnings("unused")
public class LoadBHInstruction extends OpCode {

    public LoadBHInstruction() {
        super(0x44, "LD B, H", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(registers.getH());
        return cycles;
    }
}
