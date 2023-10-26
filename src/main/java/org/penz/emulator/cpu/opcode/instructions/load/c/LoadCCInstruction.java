package org.penz.emulator.cpu.opcode.instructions.load.c;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register C into register C
 */
@SuppressWarnings("unused")
public class LoadCCInstruction extends OpCode {

    public LoadCCInstruction() {
        super(0x49, "LD C, C", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(registers.getC());
        return cycles;
    }
}
