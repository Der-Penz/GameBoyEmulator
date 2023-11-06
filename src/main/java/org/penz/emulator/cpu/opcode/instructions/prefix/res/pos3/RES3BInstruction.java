package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 3rd bit of the register B
 */
@SuppressWarnings("unused")
public class RES3BInstruction extends BitOpCode {

    public RES3BInstruction() {
        super(0x98, "RES 3, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(BitUtil.setBit(registers.getB(), 3, false));
        return cycles;
    }
}