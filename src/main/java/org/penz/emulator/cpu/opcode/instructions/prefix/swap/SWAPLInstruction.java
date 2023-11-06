package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register L
 */
@SuppressWarnings("unused")
public class SWAPLInstruction extends BitOpCode {

    public SWAPLInstruction() {
        super(0x35, "SWAP L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int highNibble = registers.getL() & 0xF0;
        int lowNibble = registers.getL() & 0x0F;
        registers.setL((lowNibble << 4) | (highNibble >> 4));
        registers.getFlags().setZ(registers.getL() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        registers.getFlags().setC(false);
        return cycles;
    }
}