package org.penz.emulator.cpu.opcode.instructions.load.e;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register C into register E
 */
@SuppressWarnings("unused")
public class LoadECInstruction extends OpCode {

    public LoadECInstruction() {
        super(0x59, "LD E, C", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(registers.getC());
        return cycles;
    }
}
