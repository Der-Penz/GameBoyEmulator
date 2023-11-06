package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos6;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 6th bit of the register E
 */
@SuppressWarnings("unused")
public class SET6EInstruction extends BitOpCode {

    public SET6EInstruction() {
        super(0xF3, "SET 6, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(BitUtil.setBit(registers.getE(), 6, true));
        return cycles;
    }
}