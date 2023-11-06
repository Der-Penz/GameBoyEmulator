package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 5th bit of the register H and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT5HInstruction extends BitOpCode {

    public BIT5HInstruction() {
        super(0x6C, "BIT 5, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit5 = BitUtil.getBit(registers.getH(), 5);
        registers.getFlags().setZ(!bit5);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}