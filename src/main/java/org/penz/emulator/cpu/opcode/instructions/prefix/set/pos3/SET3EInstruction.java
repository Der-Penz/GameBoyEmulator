package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 3rd bit of the register E
 */
@SuppressWarnings("unused")
public class SET3EInstruction extends BitOpCode {

    public SET3EInstruction() {
        super(0xDB, "SET 3, E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setE(BitUtil.setBit(registers.getE(), 3, true));
        return cycles;
    }
}