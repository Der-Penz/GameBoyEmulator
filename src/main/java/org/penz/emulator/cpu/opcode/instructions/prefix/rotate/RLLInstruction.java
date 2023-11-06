package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register L left through Carry flag.
 */
@SuppressWarnings("unused")
public class RLLInstruction extends BitOpCode {

    public RLLInstruction() {
        super(0x15, "RL L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getL(), 7);
        registers.setL(registers.getL() << 1 | (registers.getFlags().isC() ? 1 : 0));
        registers.getFlags().setC(bit7);

        registers.getFlags().setZ(registers.getL() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}