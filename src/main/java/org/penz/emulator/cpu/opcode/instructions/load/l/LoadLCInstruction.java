package org.penz.emulator.cpu.opcode.instructions.load.l;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register C into register L
 */
@SuppressWarnings("unused")
public class LoadLCInstruction extends OpCode {

    public LoadLCInstruction() {
        super(0x69, "LD L, C", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(registers.getC());
        return cycles;
    }
}
