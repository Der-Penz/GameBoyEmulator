package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 7th bit of the register E
 */
@SuppressWarnings("unused")
public class SET7EInstruction extends BitOpCode {

    public SET7EInstruction() {
        super(0xFB, "SET 7, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(BitUtil.setBit(registers.getE(), 7, true));
        return cycles;
    }
}