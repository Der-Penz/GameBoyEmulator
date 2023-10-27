package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register A into register A
 */
@SuppressWarnings("unused")
public class LoadAAInstruction extends OpCode {

    public LoadAAInstruction() {
        super(0x7F, "LD A, A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(registers.getA());
        return cycles;
    }
}
