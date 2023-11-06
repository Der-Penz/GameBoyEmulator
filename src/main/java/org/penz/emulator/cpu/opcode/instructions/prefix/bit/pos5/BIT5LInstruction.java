package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 5th bit of the register L and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT5LInstruction extends BitOpCode {

    public BIT5LInstruction() {
        super(0x6D, "BIT 5, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit5 = BitUtil.getBit(registers.getL(), 5);
        registers.getFlags().setZ(!bit5);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}