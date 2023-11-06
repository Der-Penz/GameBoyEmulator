package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos6;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 6th bit of the register L
 */
@SuppressWarnings("unused")
public class RES6LInstruction extends BitOpCode {

    public RES6LInstruction() {
        super(0xB5, "RES 6, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 6, false));
        return cycles;
    }
}