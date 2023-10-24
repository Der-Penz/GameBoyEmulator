package org.penz.emulator.cpu.opcode.instructions.load.b;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value of the address register HL points to into register B
 */
public class LoadBHLInstruction extends OpCode {

    public LoadBHLInstruction() {
        super(0x46, "LD B, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(addressSpace.readByte(registers.getHL()));
        return cycles;
    }
}
