package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 2nd bit of the register A
 */
@SuppressWarnings("unused")
public class RES2AInstruction extends BitOpCode {

    public RES2AInstruction() {
        super(0x97, "RES 2, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(BitUtil.setBit(registers.getA(), 2, false));
        return cycles;
    }
}