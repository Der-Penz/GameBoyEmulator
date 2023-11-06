package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 5th bit of the register B
 */
@SuppressWarnings("unused")
public class RES5BInstruction extends BitOpCode {

    public RES5BInstruction() {
        super(0xA8, "RES 5, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(BitUtil.setBit(registers.getB(), 5, false));
        return cycles;
    }
}