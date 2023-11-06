package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 4th bit of the register H
 */
@SuppressWarnings("unused")
public class SET4HInstruction extends BitOpCode {

    public SET4HInstruction() {
        super(0xE4, "SET 4, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 4, true));
        return cycles;
    }
}