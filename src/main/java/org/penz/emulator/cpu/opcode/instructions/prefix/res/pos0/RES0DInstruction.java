package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 0th bit of the register D
 */
@SuppressWarnings("unused")
public class RES0DInstruction extends BitOpCode {

    public RES0DInstruction() {
        super(0x82, "RES 0, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(BitUtil.setBit(registers.getD(), 0, false));
        return cycles;
    }
}