package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 1st bit of the register A
 */
@SuppressWarnings("unused")
public class RES1AInstruction extends BitOpCode {

    public RES1AInstruction() {
        super(0x8F, "RES 1, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(BitUtil.setBit(registers.getA(), 1, false));
        return cycles;
    }
}