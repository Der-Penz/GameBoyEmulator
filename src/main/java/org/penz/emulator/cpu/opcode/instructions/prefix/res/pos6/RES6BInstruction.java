package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos6;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 6th bit of the register B
 */
@SuppressWarnings("unused")
public class RES6BInstruction extends BitOpCode {

    public RES6BInstruction() {
        super(0xB0, "RES 6, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(BitUtil.setBit(registers.getB(), 6, false));
        return cycles;
    }
}