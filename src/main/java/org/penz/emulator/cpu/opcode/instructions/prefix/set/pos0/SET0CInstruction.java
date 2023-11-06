package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 0th bit of the register C
 */
@SuppressWarnings("unused")
public class SET0CInstruction extends BitOpCode {

    public SET0CInstruction() {
        super(0xC1, "SET 0, C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(BitUtil.setBit(registers.getC(), 0, true));
        return cycles;
    }
}