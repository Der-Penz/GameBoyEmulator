package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 3rd bit of the register H
 */
@SuppressWarnings("unused")
public class SET3HInstruction extends BitOpCode {

    public SET3HInstruction() {
        super(0xDC, "SET 3, H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setH(BitUtil.setBit(registers.getH(), 3, true));
        return cycles;
    }
}