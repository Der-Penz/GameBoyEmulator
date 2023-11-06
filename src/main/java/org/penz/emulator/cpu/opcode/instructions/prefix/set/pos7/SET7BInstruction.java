package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 7th bit of the register B
 */
@SuppressWarnings("unused")
public class SET7BInstruction extends BitOpCode {

    public SET7BInstruction() {
        super(0xF8, "SET 7, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(BitUtil.setBit(registers.getB(), 7, true));
        return cycles;
    }
}