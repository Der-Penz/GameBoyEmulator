package org.penz.emulator.cpu.opcode.instructions.load.l;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value of the address register HL points to into register L
 */
@SuppressWarnings("unused")
public class LoadLHLInstruction extends OpCode {

    public LoadLHLInstruction() {
        super(0x6E, "LD L, (HL)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(addressSpace.readByte(registers.getHL()));
        return cycles;
    }
}
