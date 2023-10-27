package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register D into register A
 */
@SuppressWarnings("unused")
public class LoadADInstruction extends OpCode {

    public LoadADInstruction() {
        super(0x7A, "LD A, D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(registers.getD());
        return cycles;
    }
}
