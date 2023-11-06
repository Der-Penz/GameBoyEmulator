package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 7th bit of the register A
 */
@SuppressWarnings("unused")
public class RES7AInstruction extends BitOpCode {

    public RES7AInstruction() {
        super(0xBF, "RES 7, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(BitUtil.setBit(registers.getA(), 7, false));
        return cycles;
    }
}