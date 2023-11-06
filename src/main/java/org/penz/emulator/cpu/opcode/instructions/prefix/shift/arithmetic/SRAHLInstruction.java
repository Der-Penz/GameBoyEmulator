package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift data pointed to by register HL right
 */
@SuppressWarnings("unused")
public class SRAHLInstruction extends BitOpCode {

    public SRAHLInstruction() {
        super(0x2E, "SRA (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int data = addressSpace.readByte(registers.getHL());
        boolean bit0 = BitUtil.getBit(data, 0);
        boolean bit7 = BitUtil.getBit(data, 7);
        int result = data >> 1 | (bit7 ? 1 : 0) << 7;

        addressSpace.writeByte(registers.getHL(), result);

        registers.getFlags().setC(bit0);
        registers.getFlags().setZ(result == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}