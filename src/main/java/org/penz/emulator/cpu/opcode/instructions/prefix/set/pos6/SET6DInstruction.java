package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos6;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 6th bit of the register D
 */
@SuppressWarnings("unused")
public class SET6DInstruction extends BitOpCode {

    public SET6DInstruction() {
        super(0xF2, "SET 6, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(BitUtil.setBit(registers.getD(), 6, true));
        return cycles;
    }
}