package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 7th bit of the register A and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT7AInstruction extends BitOpCode {

    public BIT7AInstruction() {
        super(0x7F, "BIT 7, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getA(), 7);
        registers.getFlags().setZ(!bit7);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}