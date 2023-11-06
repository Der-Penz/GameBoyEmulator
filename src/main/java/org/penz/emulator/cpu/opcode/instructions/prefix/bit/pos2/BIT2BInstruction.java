package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 2nd bit of the register B and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT2BInstruction extends BitOpCode {

    public BIT2BInstruction() {
        super(0x50, "BIT 2, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit2 = BitUtil.getBit(registers.getB(), 2);
        registers.getFlags().setZ(!bit2);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}