package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 1st bit of the register L and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT1LInstruction extends BitOpCode {

    public BIT1LInstruction() {
        super(0x4D, "BIT 1, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit1 = BitUtil.getBit(registers.getL(), 1);
        registers.getFlags().setZ(!bit1);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}