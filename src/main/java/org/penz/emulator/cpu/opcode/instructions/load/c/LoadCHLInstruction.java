package org.penz.emulator.cpu.opcode.instructions.load.c;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value of the address register HL points to into register C
 */
@SuppressWarnings("unused")
public class LoadCHLInstruction extends OpCode {

    public LoadCHLInstruction() {
        super(0x4E, "LD C, (HL)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(addressSpace.readByte(registers.getHL()));
        return cycles;
    }
}
