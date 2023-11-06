package org.penz.emulator.cpu.opcode.instructions.bit.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A left through Carry flag.
 */
@SuppressWarnings("unused")
public class RLA0Instruction extends OpCode {

    public RLA0Instruction() {
        super(0x17, "RLA", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getA(), 7);
        registers.setA(registers.getA() << 1 | (registers.getFlags().isC() ? 1 : 0));
        registers.getFlags().setC(bit7);

        registers.getFlags().setZ(false);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}
