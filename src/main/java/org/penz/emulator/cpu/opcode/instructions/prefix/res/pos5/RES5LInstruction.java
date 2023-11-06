package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 5th bit of the register L
 */
@SuppressWarnings("unused")
public class RES5LInstruction extends BitOpCode {

    public RES5LInstruction() {
        super(0xAD, "RES 5, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 5, false));
        return cycles;
    }
}