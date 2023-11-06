package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 3rd bit of the register D and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT3DInstruction extends BitOpCode {

    public BIT3DInstruction() {
        super(0x5A, "BIT 3, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit3 = BitUtil.getBit(registers.getD(), 3);
        registers.getFlags().setZ(!bit3);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}