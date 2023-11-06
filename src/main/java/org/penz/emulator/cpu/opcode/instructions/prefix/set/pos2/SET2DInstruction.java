package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos2;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 2nd bit of the register D
 */
@SuppressWarnings("unused")
public class SET2DInstruction extends BitOpCode {

    public SET2DInstruction() {
        super(0xD2, "SET 2, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(BitUtil.setBit(registers.getD(), 2, true));
        return cycles;
    }
}