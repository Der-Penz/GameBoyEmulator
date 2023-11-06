package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 7th bit of the register H
 */
@SuppressWarnings("unused")
public class RES7HInstruction extends BitOpCode {

    public RES7HInstruction() {
        super(0xBC, "RES 7, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 7, false));
        return cycles;
    }
}