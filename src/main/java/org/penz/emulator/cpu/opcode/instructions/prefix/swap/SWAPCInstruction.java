package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register C
 */
@SuppressWarnings("unused")
public class SWAPCInstruction extends BitOpCode {

    public SWAPCInstruction() {
        super(0x31, "SWAP C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int highNibble = registers.getC() & 0xF0;
        int lowNibble = registers.getC() & 0x0F;
        registers.setC((lowNibble << 4) | (highNibble >> 4));
        registers.getFlags().setZ(registers.getC() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        registers.getFlags().setC(false);
        return cycles;
    }
}