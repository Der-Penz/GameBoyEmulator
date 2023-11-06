package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 3rd bit of the register B
 */
@SuppressWarnings("unused")
public class SET3BInstruction extends BitOpCode {

    public SET3BInstruction() {
        super(0xD8, "SET 3, B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setB(BitUtil.setBit(registers.getB(), 3, true));
        return cycles;
    }
}