package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Logically shift the register H left
 */
@SuppressWarnings("unused")
public class SRLHInstruction extends BitOpCode {

    public SRLHInstruction() {
        super(0x3C, "SRL H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getH(), 0);
        registers.setH(registers.getH() >> 1);
        registers.getFlags().setC(bit0);
        registers.getFlags().setZ(registers.getH() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}