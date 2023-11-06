package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 0th bit of the register A
 */
@SuppressWarnings("unused")
public class SET0AInstruction extends BitOpCode {

    public SET0AInstruction() {
        super(0xC7, "SET 0, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(BitUtil.setBit(registers.getA(), 0, true));
        return cycles;
    }
}