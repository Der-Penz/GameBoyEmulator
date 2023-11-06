package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 0th bit of the register H
 */
@SuppressWarnings("unused")
public class SET0HInstruction extends BitOpCode {

    public SET0HInstruction() {
        super(0xC4, "SET 0, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 0, true));
        return cycles;
    }
}