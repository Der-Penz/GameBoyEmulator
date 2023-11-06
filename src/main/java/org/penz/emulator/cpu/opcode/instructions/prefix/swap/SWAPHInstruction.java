package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register H
 */
@SuppressWarnings("unused")
public class SWAPHInstruction extends BitOpCode {

    public SWAPHInstruction() {
        super(0x34, "SWAP H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int highNibble = registers.getH() & 0xF0;
        int lowNibble = registers.getH() & 0x0F;
        registers.setH((lowNibble << 4) | (highNibble >> 4));
        registers.getFlags().setZ(registers.getH() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        registers.getFlags().setC(false);
        return cycles;
    }
}