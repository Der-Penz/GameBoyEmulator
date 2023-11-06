package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Logically shift the register L left
 */
@SuppressWarnings("unused")
public class SRLLInstruction extends BitOpCode {

    public SRLLInstruction() {
        super(0x3D, "SRL L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getL(), 0);
        registers.setL(registers.getL() >> 1);
        registers.getFlags().setC(bit0);
        registers.getFlags().setZ(registers.getL() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}