package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 7th bit of the register L
 */
@SuppressWarnings("unused")
public class SET7LInstruction extends BitOpCode {

    public SET7LInstruction() {
        super(0xFD, "SET 7, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 7, true));
        return cycles;
    }
}