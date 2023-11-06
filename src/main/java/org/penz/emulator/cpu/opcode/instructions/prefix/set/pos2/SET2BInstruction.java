package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 2nd bit of the register B
 */
@SuppressWarnings("unused")
public class SET2BInstruction extends BitOpCode {

    public SET2BInstruction() {
        super(0xD0, "SET 2, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(BitUtil.setBit(registers.getB(), 2, true));
        return cycles;
    }
}