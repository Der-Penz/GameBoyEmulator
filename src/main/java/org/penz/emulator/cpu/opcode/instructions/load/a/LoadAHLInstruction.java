package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value of the address register HL points to into register A
 */
@SuppressWarnings("unused")
public class LoadAHLInstruction extends OpCode {

    public LoadAHLInstruction() {
        super(0x7E, "LD A, (HL)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(addressSpace.readByte(registers.getHL()));
        return cycles;
    }
}
