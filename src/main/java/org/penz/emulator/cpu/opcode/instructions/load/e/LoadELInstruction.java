package org.penz.emulator.cpu.opcode.instructions.load.e;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register L into register E
 */
@SuppressWarnings("unused")
public class LoadELInstruction extends OpCode {

    public LoadELInstruction() {
        super(0x5D, "LD E, L", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(registers.getL());
        return cycles;
    }
}
