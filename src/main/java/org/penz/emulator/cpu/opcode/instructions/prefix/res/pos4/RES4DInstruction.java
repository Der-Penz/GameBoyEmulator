package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 4th bit of the register D
 */
@SuppressWarnings("unused")
public class RES4DInstruction extends BitOpCode {

    public RES4DInstruction() {
        super(0xA2, "RES 4, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(BitUtil.setBit(registers.getD(), 4, false));
        return cycles;
    }
}