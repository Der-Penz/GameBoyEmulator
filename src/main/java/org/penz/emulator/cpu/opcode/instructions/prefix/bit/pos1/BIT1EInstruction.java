package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 1st bit of the register E and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT1EInstruction extends BitOpCode {

    public BIT1EInstruction() {
        super(0x4B, "BIT 1, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit1 = BitUtil.getBit(registers.getE(), 1);
        registers.getFlags().setZ(!bit1);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}