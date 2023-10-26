package org.penz.emulator.cpu.opcode.instructions.load.d;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register C into register D
 */
@SuppressWarnings("unused")
public class LoadDCInstruction extends OpCode {

    public LoadDCInstruction() {
        super(0x51, "LD D, C", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(registers.getC());
        return cycles;
    }
}
