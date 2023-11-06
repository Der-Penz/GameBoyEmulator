package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 4th bit of the register E
 */
@SuppressWarnings("unused")
public class RES4EInstruction extends BitOpCode {

    public RES4EInstruction() {
        super(0xA3, "RES 4, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(BitUtil.setBit(registers.getE(), 4, false));
        return cycles;
    }
}