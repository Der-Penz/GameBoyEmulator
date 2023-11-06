package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 7th bit of the register L
 */
@SuppressWarnings("unused")
public class RES7LInstruction extends BitOpCode {

    public RES7LInstruction() {
        super(0xBD, "RES 7, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 7, false));
        return cycles;
    }
}