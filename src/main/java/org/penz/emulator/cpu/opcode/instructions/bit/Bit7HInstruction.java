package org.penz.emulator.cpu.opcode.instructions.bit;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Check if bit 7 of register H is set or not and set flags accordingly
 */
public class Bit7HInstruction extends BitOpCode {

    public Bit7HInstruction() {
        super(0x7C, "BIT 7, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getH(), 7);

        registers.getFlags().setZ(!bit7);
        registers.getFlags().setN(false);
        registers.getFlags().setH(true);
        return cycles;
    }
}
