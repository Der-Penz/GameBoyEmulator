package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 4th bit of the register H
 */
@SuppressWarnings("unused")
public class RES4HInstruction extends BitOpCode {

    public RES4HInstruction() {
        super(0xA4, "RES 4, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 4, false));
        return cycles;
    }
}