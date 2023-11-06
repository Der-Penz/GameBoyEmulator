package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 1st bit of the register A
 */
@SuppressWarnings("unused")
public class SET1AInstruction extends BitOpCode {

    public SET1AInstruction() {
        super(0xCF, "SET 1, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(BitUtil.setBit(registers.getA(), 1, true));
        return cycles;
    }
}