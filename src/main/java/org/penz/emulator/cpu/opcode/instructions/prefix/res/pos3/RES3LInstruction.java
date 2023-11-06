package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 3rd bit of the register L
 */
@SuppressWarnings("unused")
public class RES3LInstruction extends BitOpCode {

    public RES3LInstruction() {
        super(0x9D, "RES 3, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 3, false));
        return cycles;
    }
}