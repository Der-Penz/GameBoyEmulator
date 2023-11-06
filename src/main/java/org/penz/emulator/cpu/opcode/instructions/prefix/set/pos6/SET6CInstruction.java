package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos6;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 6th bit of the register C
 */
@SuppressWarnings("unused")
public class SET6CInstruction extends BitOpCode {

    public SET6CInstruction() {
        super(0xF1, "SET 6, C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(BitUtil.setBit(registers.getC(), 6, true));
        return cycles;
    }
}