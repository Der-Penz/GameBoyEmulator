package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 0th bit of the register B
 */
@SuppressWarnings("unused")
public class SET0BInstruction extends BitOpCode {

    public SET0BInstruction() {
        super(0xC0, "SET 0, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(BitUtil.setBit(registers.getB(), 0, true));
        return cycles;
    }
}