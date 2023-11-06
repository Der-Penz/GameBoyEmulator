package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate data located where HL points to right. Old bit 0 to Carry flag and bit 7.
 */
@SuppressWarnings("unused")
public class RRCHLInstruction extends BitOpCode {

    public RRCHLInstruction() {
        super(0x0E, "RRC (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int data = addressSpace.readByte(registers.getHL());

        boolean bit0 = BitUtil.getBit(registers.getHL(), 0);

        int rotatedData = data >> 1 | (bit0 ? 1 : 0) << 7;
        addressSpace.writeByte(registers.getHL(), rotatedData);
        registers.getFlags().setC(bit0);

        registers.getFlags().setZ(rotatedData == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}