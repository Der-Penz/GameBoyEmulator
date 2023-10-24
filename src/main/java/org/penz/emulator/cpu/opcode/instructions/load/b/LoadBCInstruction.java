package org.penz.emulator.cpu.opcode.instructions.load.b;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register C into register B
 */
public class LoadBCInstruction extends OpCode {

    public LoadBCInstruction() {
        super(0x41, "LD B, C", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(registers.getC());
        return cycles;
    }
}
