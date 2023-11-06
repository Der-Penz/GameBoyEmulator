package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 7th bit of the register B
 */
@SuppressWarnings("unused")
public class RES7BInstruction extends BitOpCode {

    public RES7BInstruction() {
        super(0xB8, "RES 7, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(BitUtil.setBit(registers.getB(), 7, false));
        return cycles;
    }
}