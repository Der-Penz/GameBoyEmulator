package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 5th bit of the register D
 */
@SuppressWarnings("unused")
public class RES5DInstruction extends BitOpCode {

    public RES5DInstruction() {
        super(0xAA, "RES 5, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(BitUtil.setBit(registers.getD(), 5, false));
        return cycles;
    }
}