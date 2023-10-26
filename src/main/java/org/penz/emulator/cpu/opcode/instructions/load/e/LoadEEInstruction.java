package org.penz.emulator.cpu.opcode.instructions.load.e;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register E into register E
 */
@SuppressWarnings("unused")
public class LoadEEInstruction extends OpCode {

    public LoadEEInstruction() {
        super(0x5B, "LD E, E", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(registers.getE());
        return cycles;
    }
}
