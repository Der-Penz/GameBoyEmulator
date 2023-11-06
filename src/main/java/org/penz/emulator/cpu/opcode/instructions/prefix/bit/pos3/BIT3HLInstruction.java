package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 3rd bit of the data pointed to by register HL and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT3HLInstruction extends BitOpCode {

    public BIT3HLInstruction() {
        super(0x5E, "BIT 3, (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit3 = BitUtil.getBit(addressSpace.readByte(registers.getHL()), 3);
        registers.getFlags().setZ(!bit3);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}