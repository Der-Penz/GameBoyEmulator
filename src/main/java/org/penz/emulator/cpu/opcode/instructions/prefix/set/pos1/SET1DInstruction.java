package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 1st bit of the register D
 */
@SuppressWarnings("unused")
public class SET1DInstruction extends BitOpCode {

    public SET1DInstruction() {
        super(0xCA, "SET 1, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(BitUtil.setBit(registers.getD(), 1, true));
        return cycles;
    }
}