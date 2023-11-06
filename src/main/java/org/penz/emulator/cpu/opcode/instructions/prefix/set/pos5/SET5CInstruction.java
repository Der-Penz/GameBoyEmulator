package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 5th bit of the register C
 */
@SuppressWarnings("unused")
public class SET5CInstruction extends BitOpCode {

    public SET5CInstruction() {
        super(0xE9, "SET 5, C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(BitUtil.setBit(registers.getC(), 5, true));
        return cycles;
    }
}