package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 5th bit of the register L
 */
@SuppressWarnings("unused")
public class SET5LInstruction extends BitOpCode {

    public SET5LInstruction() {
        super(0xED, "SET 5, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 5, true));
        return cycles;
    }
}