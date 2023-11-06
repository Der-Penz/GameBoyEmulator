package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 7th bit of the register B and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT7BInstruction extends BitOpCode {

    public BIT7BInstruction() {
        super(0x78, "BIT 7, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getB(), 7);
        registers.getFlags().setZ(!bit7);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}