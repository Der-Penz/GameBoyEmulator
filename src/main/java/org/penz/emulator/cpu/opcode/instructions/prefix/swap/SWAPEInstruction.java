package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register E
 */
@SuppressWarnings("unused")
public class SWAPEInstruction extends BitOpCode {

    public SWAPEInstruction() {
        super(0x33, "SWAP E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int highNibble = registers.getE() & 0xF0;
        int lowNibble = registers.getE() & 0x0F;
        registers.setE((lowNibble << 4) | (highNibble >> 4));
        registers.getFlags().setZ(registers.getE() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        registers.getFlags().setC(false);
        return cycles;
    }
}