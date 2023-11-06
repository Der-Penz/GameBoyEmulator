package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 2nd bit of the register C
 */
@SuppressWarnings("unused")
public class RES2CInstruction extends BitOpCode {

    public RES2CInstruction() {
        super(0x91, "RES 2, C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(BitUtil.setBit(registers.getC(), 2, false));
        return cycles;
    }
}