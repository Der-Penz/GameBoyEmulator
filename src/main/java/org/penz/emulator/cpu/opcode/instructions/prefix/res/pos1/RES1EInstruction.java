package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 1st bit of the register E
 */
@SuppressWarnings("unused")
public class RES1EInstruction extends BitOpCode {

    public RES1EInstruction() {
        super(0x8B, "RES 1, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(BitUtil.setBit(registers.getE(), 1, false));
        return cycles;
    }
}