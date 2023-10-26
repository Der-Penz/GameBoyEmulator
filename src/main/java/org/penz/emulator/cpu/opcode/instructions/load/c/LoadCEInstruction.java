package org.penz.emulator.cpu.opcode.instructions.load.c;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register E into register C
 */
@SuppressWarnings("unused")
public class LoadCEInstruction extends OpCode {

    public LoadCEInstruction() {
        super(0x4B, "LD C, E", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(registers.getE());
        return cycles;
    }
}
