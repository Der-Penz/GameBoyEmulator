package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 3rd bit of the register A
 */
@SuppressWarnings("unused")
public class SET3AInstruction extends BitOpCode {

    public SET3AInstruction() {
        super(0xDF, "SET 3, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(BitUtil.setBit(registers.getA(), 3, true));
        return cycles;
    }
}