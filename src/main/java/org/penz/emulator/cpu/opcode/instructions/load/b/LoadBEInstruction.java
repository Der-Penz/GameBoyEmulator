package org.penz.emulator.cpu.opcode.instructions.load.b;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register E into register B
 */
public class LoadBEInstruction extends OpCode {

    public LoadBEInstruction() {
        super(0x43, "LD B, E", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(registers.getE());
        return cycles;
    }
}
