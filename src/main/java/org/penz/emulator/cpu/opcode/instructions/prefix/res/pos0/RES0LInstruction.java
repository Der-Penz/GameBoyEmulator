package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 0th bit of the register L
 */
@SuppressWarnings("unused")
public class RES0LInstruction extends BitOpCode {

    public RES0LInstruction() {
        super(0x85, "RES 0, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 0, false));
        return cycles;
    }
}