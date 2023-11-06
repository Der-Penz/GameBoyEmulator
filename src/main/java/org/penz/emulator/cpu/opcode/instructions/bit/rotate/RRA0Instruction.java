package org.penz.emulator.cpu.opcode.instructions.bit.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A right through Carry flag.
 */
@SuppressWarnings("unused")
public class RRA0Instruction extends OpCode {

    public RRA0Instruction() {
        super(0x1F, "RRA", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getA(), 0);
        registers.setA(registers.getA() >> 1 | (registers.getFlags().isC() ? 1 : 0) << 7);
        registers.getFlags().setC(bit0);

        registers.getFlags().setZ(false);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}