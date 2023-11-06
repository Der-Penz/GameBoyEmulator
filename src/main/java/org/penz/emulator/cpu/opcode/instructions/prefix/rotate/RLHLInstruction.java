package org.penz.emulator.cpu.opcode.instructions.prefix.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate data located where HL points to left through Carry flag.
 */
@SuppressWarnings("unused")
public class RLHLInstruction extends BitOpCode {

    public RLHLInstruction() {
        super(0x16, "RL (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int data = addressSpace.readByte(registers.getHL());

        boolean bit7 = BitUtil.getBit(data, 7);

        int rotatedData = data << 1 | (registers.getFlags().isC() ? 1 : 0);
        addressSpace.writeByte(registers.getHL(), rotatedData);
        registers.getFlags().setC(bit7);

        registers.getFlags().setZ(rotatedData == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}