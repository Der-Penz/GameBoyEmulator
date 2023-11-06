package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 1st bit of the register C and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT1CInstruction extends BitOpCode {

    public BIT1CInstruction() {
        super(0x49, "BIT 1, C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit1 = BitUtil.getBit(registers.getC(), 1);
        registers.getFlags().setZ(!bit1);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}