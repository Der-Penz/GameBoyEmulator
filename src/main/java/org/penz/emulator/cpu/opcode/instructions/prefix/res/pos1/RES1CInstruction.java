package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 1st bit of the register C
 */
@SuppressWarnings("unused")
public class RES1CInstruction extends BitOpCode {

    public RES1CInstruction() {
        super(0x89, "RES 1, C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(BitUtil.setBit(registers.getC(), 1, false));
        return cycles;
    }
}