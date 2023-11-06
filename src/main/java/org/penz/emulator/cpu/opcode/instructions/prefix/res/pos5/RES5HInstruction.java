package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 5th bit of the register H
 */
@SuppressWarnings("unused")
public class RES5HInstruction extends BitOpCode {

    public RES5HInstruction() {
        super(0xAC, "RES 5, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 5, false));
        return cycles;
    }
}