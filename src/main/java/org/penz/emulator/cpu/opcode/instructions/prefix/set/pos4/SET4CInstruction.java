package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 4th bit of the register C
 */
@SuppressWarnings("unused")
public class SET4CInstruction extends BitOpCode {

    public SET4CInstruction() {
        super(0xE1, "SET 4, C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setC(BitUtil.setBit(registers.getC(), 4, true));
        return cycles;
    }
}