package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register H into register A
 */
@SuppressWarnings("unused")
public class LoadAHInstruction extends OpCode {

    public LoadAHInstruction() {
        super(0x7C, "LD A, H", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(registers.getH());
        return cycles;
    }
}
