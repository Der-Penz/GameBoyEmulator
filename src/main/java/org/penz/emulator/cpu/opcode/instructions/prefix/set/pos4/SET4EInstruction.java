package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 4th bit of the register E
 */
@SuppressWarnings("unused")
public class SET4EInstruction extends BitOpCode {

    public SET4EInstruction() {
        super(0xE3, "SET 4, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(BitUtil.setBit(registers.getE(), 4, true));
        return cycles;
    }
}