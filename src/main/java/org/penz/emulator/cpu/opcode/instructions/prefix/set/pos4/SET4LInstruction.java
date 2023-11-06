package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 4th bit of the register L
 */
@SuppressWarnings("unused")
public class SET4LInstruction extends BitOpCode {

    public SET4LInstruction() {
        super(0xE5, "SET 4, L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(BitUtil.setBit(registers.getL(), 4, true));
        return cycles;
    }
}