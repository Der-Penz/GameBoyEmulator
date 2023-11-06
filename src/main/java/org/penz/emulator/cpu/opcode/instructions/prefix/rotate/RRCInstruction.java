package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register C right through Carry flag.
 */
@SuppressWarnings("unused")
public class RRCInstruction extends BitOpCode {

    public RRCInstruction() {
        super(0x19, "RR C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getC(), 0);
        registers.setC(registers.getC() >> 1 | (registers.getFlags().isC() ? 1 : 0) << 7);
        registers.getFlags().setC(bit0);

        registers.getFlags().setZ(registers.getC() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}