package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 2nd bit of the register B
 */
@SuppressWarnings("unused")
public class RES2BInstruction extends BitOpCode {

    public RES2BInstruction() {
        super(0x90, "RES 2, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(BitUtil.setBit(registers.getB(), 2, false));
        return cycles;
    }
}