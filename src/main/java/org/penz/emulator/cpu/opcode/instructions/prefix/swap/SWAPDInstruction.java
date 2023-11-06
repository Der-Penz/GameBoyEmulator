package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register D
 */
@SuppressWarnings("unused")
public class SWAPDInstruction extends BitOpCode {

    public SWAPDInstruction() {
        super(0x32, "SWAP D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int highNibble = registers.getD() & 0xF0;
        int lowNibble = registers.getD() & 0x0F;
        registers.setD((lowNibble << 4) | (highNibble >> 4));
        registers.getFlags().setZ(registers.getD() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        registers.getFlags().setC(false);
        return cycles;
    }
}