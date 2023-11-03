package org.penz.emulator.cpu.opcode.instructions.bit.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A left. Old bit 7 to Carry flag and bit 0.
 */
@SuppressWarnings("unused")
public class RLCAInstruction extends BitOpCode {

    public RLCAInstruction() {
        super(0x07, "RLCA", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getA(), 7);
        registers.setA(registers.getA() << 1 | (bit7 ? 1 : 0));
        registers.getFlags().setC(bit7);

        registers.getFlags().setZ(false);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}
