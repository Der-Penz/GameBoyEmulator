package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register H right. Old bit 0 to Carry flag and bit 7.
 */
@SuppressWarnings("unused")
public class RRCHInstruction extends BitOpCode {

    public RRCHInstruction() {
        super(0x0C, "RRC H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getH(), 0);
        registers.setH(registers.getH() >> 1 | (bit0 ? 1 : 0) << 7);
        registers.getFlags().setC(bit0);

        registers.getFlags().setZ(registers.getH() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}