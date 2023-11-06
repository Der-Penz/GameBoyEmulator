package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos6;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 6th bit of the register H and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT6HInstruction extends BitOpCode {

    public BIT6HInstruction() {
        super(0x74, "BIT 6, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit6 = BitUtil.getBit(registers.getH(), 6);
        registers.getFlags().setZ(!bit6);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}