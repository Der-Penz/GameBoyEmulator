package org.penz.emulator.cpu.opcode.instructions.load.c;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register L into register C
 */
@SuppressWarnings("unused")
public class LoadCLInstruction extends OpCode {

    public LoadCLInstruction() {
        super(0x4D, "LD C, L", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(registers.getL());
        return cycles;
    }
}
