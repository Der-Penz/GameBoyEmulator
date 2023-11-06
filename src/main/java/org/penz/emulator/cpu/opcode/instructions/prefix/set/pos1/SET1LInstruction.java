package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 1st bit of the register L
 */
@SuppressWarnings("unused")
public class SET1LInstruction extends BitOpCode {

    public SET1LInstruction() {
        super(0xCD, "SET 1, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 1, true));
        return cycles;
    }
}