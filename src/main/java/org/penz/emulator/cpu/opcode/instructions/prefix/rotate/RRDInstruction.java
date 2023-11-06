package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register D right through Carry flag.
 */
@SuppressWarnings("unused")
public class RRDInstruction extends BitOpCode {

    public RRDInstruction() {
        super(0x1A, "RR D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit0 = BitUtil.getBit(registers.getD(), 0);
        registers.setD(registers.getD() >> 1 | (registers.getFlags().isC() ? 1 : 0) << 7);
        registers.getFlags().setC(bit0);

        registers.getFlags().setZ(registers.getD() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}