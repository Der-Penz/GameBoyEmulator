package org.penz.emulator.cpu.opcode.instructions.load.c;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register H into register C
 */
@SuppressWarnings("unused")
public class LoadCHInstruction extends OpCode {

    public LoadCHInstruction() {
        super(0x4C, "LD C, H", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(registers.getH());
        return cycles;
    }
}
