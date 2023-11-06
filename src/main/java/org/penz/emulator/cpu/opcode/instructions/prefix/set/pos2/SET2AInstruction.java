package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 2nd bit of the register A
 */
@SuppressWarnings("unused")
public class SET2AInstruction extends BitOpCode {

    public SET2AInstruction() {
        super(0xD7, "SET 2, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(BitUtil.setBit(registers.getA(), 2, true));
        return cycles;
    }
}