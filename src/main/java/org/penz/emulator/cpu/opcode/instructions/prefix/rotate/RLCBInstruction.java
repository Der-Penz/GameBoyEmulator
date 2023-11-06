package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register B left. Old bit 7 to Carry flag and bit 0.
 */
@SuppressWarnings("unused")
public class RLCBInstruction extends BitOpCode {

    public RLCBInstruction() {
        super(0x00, "RLC B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getB(), 7);
        registers.setB(registers.getB() << 1 | (bit7 ? 1 : 0));
        registers.getFlags().setC(bit7);

        registers.getFlags().setZ(registers.getB() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}