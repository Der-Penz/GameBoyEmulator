package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register B into register A
 */
@SuppressWarnings("unused")
public class LoadABInstruction extends OpCode {

    public LoadABInstruction() {
        super(0x78, "LD A, B", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(registers.getB());
        return cycles;
    }
}
