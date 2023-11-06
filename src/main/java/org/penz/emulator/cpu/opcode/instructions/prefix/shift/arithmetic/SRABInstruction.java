package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register B right
 */
@SuppressWarnings("unused")
public class SRABInstruction extends BitOpCode {

    public SRABInstruction() {
        super(0x28, "SRA B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getB(), 0);
        boolean bit7 = BitUtil.getBit(registers.getB(), 7);
        registers.setB(registers.getB() >> 1 | (bit7 ? 1 : 0) << 7);
        registers.getFlags().setC(bit0);
        registers.getFlags().setZ(registers.getB() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}