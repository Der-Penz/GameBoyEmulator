package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 2nd bit of the register H
 */
@SuppressWarnings("unused")
public class RES2HInstruction extends BitOpCode {

    public RES2HInstruction() {
        super(0x94, "RES 2, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 2, false));
        return cycles;
    }
}