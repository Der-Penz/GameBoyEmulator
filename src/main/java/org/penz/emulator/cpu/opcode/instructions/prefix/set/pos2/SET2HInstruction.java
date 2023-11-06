package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 2nd bit of the register H
 */
@SuppressWarnings("unused")
public class SET2HInstruction extends BitOpCode {

    public SET2HInstruction() {
        super(0xD4, "SET 2, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 2, true));
        return cycles;
    }
}