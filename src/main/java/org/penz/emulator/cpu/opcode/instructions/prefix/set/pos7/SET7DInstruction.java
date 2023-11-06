package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos7;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 7th bit of the register D
 */
@SuppressWarnings("unused")
public class SET7DInstruction extends BitOpCode {

    public SET7DInstruction() {
        super(0xFA, "SET 7, D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(BitUtil.setBit(registers.getD(), 7, true));
        return cycles;
    }
}