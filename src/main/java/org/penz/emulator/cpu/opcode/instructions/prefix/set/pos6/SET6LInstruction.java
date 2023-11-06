package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos6;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 6th bit of the register L
 */
@SuppressWarnings("unused")
public class SET6LInstruction extends BitOpCode {

    public SET6LInstruction() {
        super(0xF5, "SET 6, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 6, true));
        return cycles;
    }
}