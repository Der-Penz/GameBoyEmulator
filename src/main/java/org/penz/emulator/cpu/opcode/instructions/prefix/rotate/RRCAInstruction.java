package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A right. Old bit 0 to Carry flag and bit 7.
 */
@SuppressWarnings("unused")
public class RRCAInstruction extends BitOpCode {

    public RRCAInstruction() {
        super(0x0F, "RRC A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getA(), 0);
        registers.setL(registers.getA() >> 1 | (bit0 ? 1 : 0) << 7);
        registers.getFlags().setC(bit0);

        registers.getFlags().setZ(registers.getA() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}