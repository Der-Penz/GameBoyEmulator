package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 1st bit of the register H
 */
@SuppressWarnings("unused")
public class RES1HInstruction extends BitOpCode {

    public RES1HInstruction() {
        super(0x8C, "RES 1, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 1, false));
        return cycles;
    }
}