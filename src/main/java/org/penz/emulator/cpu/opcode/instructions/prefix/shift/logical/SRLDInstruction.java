package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Logically shift the register D left
 */
@SuppressWarnings("unused")
public class SRLDInstruction extends BitOpCode {

    public SRLDInstruction() {
        super(0x3A, "SRL D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getD(), 0);
        registers.setD(registers.getD() >> 1);
        registers.getFlags().setC(bit0);
        registers.getFlags().setZ(registers.getD() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}