package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register E left. Old bit 7 to Carry flag and bit 0.
 */
@SuppressWarnings("unused")
public class RLCEInstruction extends BitOpCode {

    public RLCEInstruction() {
        super(0x03, "RLC E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        boolean bit7 = BitUtil.getBit(registers.getE(), 7);
        registers.setE(registers.getE() << 1 | (bit7 ? 1 : 0));
        registers.getFlags().setC(bit7);

        registers.getFlags().setZ(registers.getE() == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}