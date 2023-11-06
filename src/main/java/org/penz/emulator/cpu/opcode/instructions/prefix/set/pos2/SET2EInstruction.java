package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 2nd bit of the register E
 */
@SuppressWarnings("unused")
public class SET2EInstruction extends BitOpCode {

    public SET2EInstruction() {
        super(0xD3, "SET 2, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(BitUtil.setBit(registers.getE(), 2, true));
        return cycles;
    }
}