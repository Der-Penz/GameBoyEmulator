package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register   E left
 */
@SuppressWarnings("unused")
public class SLAEInstruction extends BitOpCode {

    public SLAEInstruction() {
        super(0x23, "SLA E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getE(), 7);
        registers.setE(registers.getE() << 1);
        registers.getFlags().setC(bit7);
        registers.getFlags().setZ(registers.getE() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}