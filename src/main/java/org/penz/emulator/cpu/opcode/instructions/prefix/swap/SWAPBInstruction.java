package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register B
 */
@SuppressWarnings("unused")
public class SWAPBInstruction extends BitOpCode {

    public SWAPBInstruction() {
        super(0x30, "SWAP B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int highNibble = registers.getB() & 0xF0;
        int lowNibble = registers.getB() & 0x0F;
        registers.setB((lowNibble << 4) | (highNibble >> 4));
        registers.getFlags().setZ(registers.getB() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        registers.getFlags().setC(false);
        return cycles;
    }
}