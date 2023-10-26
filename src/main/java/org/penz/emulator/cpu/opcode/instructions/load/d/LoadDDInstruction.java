package org.penz.emulator.cpu.opcode.instructions.load.d;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register D into register D
 */
@SuppressWarnings("unused")
public class LoadDDInstruction extends OpCode {

    public LoadDDInstruction() {
        super(0x52, "LD D, D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(registers.getD());
        return cycles;
    }
}
