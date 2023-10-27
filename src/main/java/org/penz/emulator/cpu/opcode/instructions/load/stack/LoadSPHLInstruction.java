package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value of HL into SP
 */
@SuppressWarnings("unused")
public class LoadSPHLInstruction extends OpCode {

    public LoadSPHLInstruction() {
        super(0xF9, "Load SP, HL", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setSP(registers.getHL());
        return cycles;
    }
}
