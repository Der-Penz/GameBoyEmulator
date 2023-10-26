package org.penz.emulator.cpu.opcode.instructions.load.d;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register E into register D
 */
@SuppressWarnings("unused")
public class LoadDEInstruction extends OpCode {

    public LoadDEInstruction() {
        super(0x53, "LD D, E", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(registers.getE());
        return cycles;
    }
}
