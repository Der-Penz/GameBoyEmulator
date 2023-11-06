package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 0th bit of the register H
 */
@SuppressWarnings("unused")
public class RES0HInstruction extends BitOpCode {

    public RES0HInstruction() {
        super(0x84, "RES 0, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 0, false));
        return cycles;
    }
}