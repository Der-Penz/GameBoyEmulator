package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 2nd bit of the register E
 */
@SuppressWarnings("unused")
public class RES2EInstruction extends BitOpCode {

    public RES2EInstruction() {
        super(0x93, "RES 2, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(BitUtil.setBit(registers.getE(), 2, false));
        return cycles;
    }
}