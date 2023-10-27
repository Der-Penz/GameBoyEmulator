package org.penz.emulator.cpu.opcode.instructions.load.l;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register H into register L
 */
@SuppressWarnings("unused")
public class LoadLHInstruction extends OpCode {

    public LoadLHInstruction() {
        super(0x6C, "LD L, H", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(registers.getH());
        return cycles;
    }
}
