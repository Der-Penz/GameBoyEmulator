package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 0th bit of the register B and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT0BInstruction extends BitOpCode {

    public BIT0BInstruction() {
        super(0x40, "BIT 0, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getB(), 0);
        registers.getFlags().setZ(!bit0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}