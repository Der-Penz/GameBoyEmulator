package org.penz.emulator.cpu.opcode.instructions.prefix.bit.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Get the 4th bit of the data pointed to by register HL and set flags according to value of that bit
 */
@SuppressWarnings("unused")
public class BIT4HLInstruction extends BitOpCode {

    public BIT4HLInstruction() {
        super(0x66, "BIT 4, (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit4 = BitUtil.getBit(addressSpace.readByte(registers.getHL()), 4);
        registers.getFlags().setZ(!bit4);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}