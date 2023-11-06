package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos6;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 6th bit of the register H
 */
@SuppressWarnings("unused")
public class RES6HInstruction extends BitOpCode {

    public RES6HInstruction() {
        super(0xB4, "RES 6, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 6, false));
        return cycles;
    }
}