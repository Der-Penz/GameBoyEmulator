package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 3rd bit of the register A
 */
@SuppressWarnings("unused")
public class RES3AInstruction extends BitOpCode {

    public RES3AInstruction() {
        super(0x9F, "RES 3, A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(BitUtil.setBit(registers.getA(), 3, false));
        return cycles;
    }
}