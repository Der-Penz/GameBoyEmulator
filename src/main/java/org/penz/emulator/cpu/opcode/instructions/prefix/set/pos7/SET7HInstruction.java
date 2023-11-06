package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 7th bit of the register H
 */
@SuppressWarnings("unused")
public class SET7HInstruction extends BitOpCode {

    public SET7HInstruction() {
        super(0xFC, "SET 7, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 7, true));
        return cycles;
    }
}