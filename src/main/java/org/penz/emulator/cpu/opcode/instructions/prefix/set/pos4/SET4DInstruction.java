package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 4th bit of the register D
 */
@SuppressWarnings("unused")
public class SET4DInstruction extends BitOpCode {

    public SET4DInstruction() {
        super(0xE2, "SET 4, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(BitUtil.setBit(registers.getD(), 4, true));
        return cycles;
    }
}