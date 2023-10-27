package org.penz.emulator.cpu.opcode.instructions.load.l;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register L into register L
 */
@SuppressWarnings("unused")
public class LoadLLInstruction extends OpCode {

    public LoadLLInstruction() {
        super(0x6D, "LD L, L", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(registers.getL());
        return cycles;
    }
}
