package org.penz.emulator.cpu.opcode.instructions.load.l;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register E into register L
 */
@SuppressWarnings("unused")
public class LoadLEInstruction extends OpCode {

    public LoadLEInstruction() {
        super(0x6B, "LD L, E", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(registers.getE());
        return cycles;
    }
}
