package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 0th bit of the register E
 */
@SuppressWarnings("unused")
public class RES0EInstruction extends BitOpCode {

    public RES0EInstruction() {
        super(0x83, "RES 0, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(BitUtil.setBit(registers.getE(), 0, false));
        return cycles;
    }
}