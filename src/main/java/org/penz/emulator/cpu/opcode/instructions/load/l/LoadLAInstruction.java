package org.penz.emulator.cpu.opcode.instructions.load.l;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register A into register L
 */
@SuppressWarnings("unused")
public class LoadLAInstruction extends OpCode {

    public LoadLAInstruction() {
        super(0x6F, "LD L, A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(registers.getA());
        return cycles;
    }
}
