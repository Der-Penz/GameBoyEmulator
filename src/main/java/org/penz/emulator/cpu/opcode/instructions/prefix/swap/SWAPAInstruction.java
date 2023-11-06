package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register A
 */
@SuppressWarnings("unused")
public class SWAPAInstruction extends BitOpCode {

    public SWAPAInstruction() {
        super(0x37, "SWAP A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int highNibble = registers.getA() & 0xF0;
        int lowNibble = registers.getA() & 0x0F;
        registers.setA((lowNibble << 4) | (highNibble >> 4));
        registers.getFlags().setZ(registers.getA() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        registers.getFlags().setC(false);
        return cycles;
    }
}