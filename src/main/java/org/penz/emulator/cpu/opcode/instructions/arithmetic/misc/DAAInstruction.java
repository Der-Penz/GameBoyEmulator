package org.penz.emulator.cpu.opcode.instructions.arithmetic.misc;

import org.penz.emulator.Constants;
import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decimal Adjust Accumulator after addition or subtraction
 */
@SuppressWarnings("unused")
public class DAAInstruction extends OpCode {
    public DAAInstruction() {
        super(0x27, "DAA", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int newA = registers.getA();
        if (BitUtil.getLSByte(registers.getA()) > 9 || registers.getFlags().isH()) {
            newA = registers.getA() + (registers.getFlags().isN() ? -0x06 : 0x06);
        }
        if (BitUtil.getMSByte(registers.getA()) > 9 || registers.getFlags().isC()) {
            int msb = registers.getA() & 0xF0;
            newA = (msb + (registers.getFlags().isN() ? -0x06 : 0x06)) << 4 | (registers.getA() & 0x0F);
        }
        registers.getFlags().setH(false);
        registers.getFlags().setZ(registers.getA() == 0);
        registers.getFlags().setC(newA > Constants.BYTE_MAX_VALUE);
        registers.setA(BitUtil.toByte(newA));

        return cycles;
    }
}
