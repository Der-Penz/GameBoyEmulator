package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register  A left
 */
@SuppressWarnings("unused")
public class SLAAInstruction extends BitOpCode {

    public SLAAInstruction() {
        super(0x27, "SLA A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getA(), 7);
        registers.setA(registers.getA() << 1);
        registers.getFlags().setC(bit7);
        registers.getFlags().setZ(registers.getA() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}