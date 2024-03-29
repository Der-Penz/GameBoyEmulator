package org.penz.emulator.cpu.opcode.instructions.load.d;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value of the address register HL points to into register D
 */
@SuppressWarnings("unused")
public class LoadDHLInstruction extends OpCode {

    public LoadDHLInstruction() {
        super(0x56, "LD D, (HL)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(addressSpace.readByte(registers.getHL()));
        return cycles;
    }
}
