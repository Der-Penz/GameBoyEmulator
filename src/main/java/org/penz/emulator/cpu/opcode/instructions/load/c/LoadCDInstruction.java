package org.penz.emulator.cpu.opcode.instructions.load.c;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register D into register C
 */
@SuppressWarnings("unused")
public class LoadCDInstruction extends OpCode {

    public LoadCDInstruction() {
        super(0x4A, "LD C, D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(registers.getD());
        return cycles;
    }
}
