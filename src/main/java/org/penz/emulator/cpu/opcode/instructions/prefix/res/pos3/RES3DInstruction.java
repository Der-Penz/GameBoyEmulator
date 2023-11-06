package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 3rd bit of the register D
 */
@SuppressWarnings("unused")
public class RES3DInstruction extends BitOpCode {

    public RES3DInstruction() {
        super(0x9A, "RES 3, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(BitUtil.setBit(registers.getD(), 3, false));
        return cycles;
    }
}