package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register L into register A
 */
@SuppressWarnings("unused")
public class LoadALInstruction extends OpCode {

    public LoadALInstruction() {
        super(0x7D, "LD A, L", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(registers.getL());
        return cycles;
    }
}
