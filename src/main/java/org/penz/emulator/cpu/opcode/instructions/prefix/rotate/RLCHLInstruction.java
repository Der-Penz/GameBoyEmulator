package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate data located where HL points to left. Old bit 7 to Carry flag and bit 0.
 */
@SuppressWarnings("unused")
public class RLCHLInstruction extends BitOpCode {

    public RLCHLInstruction() {
        super(0x06, "RLC (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int data = addressSpace.readByte(registers.getHL());

        boolean bit7 = BitUtil.getBit(registers.getHL(), 7);

        int rotatedData = data << 1 | (bit7 ? 1 : 0);
        addressSpace.writeByte(registers.getHL(), rotatedData);
        registers.getFlags().setC(bit7);

        registers.getFlags().setZ(rotatedData == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}