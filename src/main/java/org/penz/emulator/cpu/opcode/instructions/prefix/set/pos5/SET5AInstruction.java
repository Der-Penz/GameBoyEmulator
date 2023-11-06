package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 5th bit of the register A
 */
@SuppressWarnings("unused")
public class SET5AInstruction extends BitOpCode {

    public SET5AInstruction() {
        super(0xEF, "SET 5, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(BitUtil.setBit(registers.getA(), 5, true));
        return cycles;
    }
}