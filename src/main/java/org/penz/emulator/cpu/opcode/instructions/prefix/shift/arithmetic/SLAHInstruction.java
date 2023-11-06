package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register   H left
 */
@SuppressWarnings("unused")
public class SLAHInstruction extends BitOpCode {

    public SLAHInstruction() {
        super(0x24, "SLA H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getH(), 7);
        registers.setH(registers.getH() << 1);
        registers.getFlags().setC(bit7);
        registers.getFlags().setZ(registers.getH() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}