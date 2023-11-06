package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Logically shift the register E left
 */
@SuppressWarnings("unused")
public class SRLEInstruction extends BitOpCode {

    public SRLEInstruction() {
        super(0x3B, "SRL E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getE(), 0);
        registers.setE(registers.getE() >> 1);
        registers.getFlags().setC(bit0);
        registers.getFlags().setZ(registers.getE() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}