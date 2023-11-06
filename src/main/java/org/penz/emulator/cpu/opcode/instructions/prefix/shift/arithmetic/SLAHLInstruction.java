package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift data pointed to by register HL left
 */
@SuppressWarnings("unused")
public class SLAHLInstruction extends BitOpCode {

    public SLAHLInstruction() {
        super(0x26, "SLA (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int data = addressSpace.readByte(registers.getHL());
        boolean bit7 = BitUtil.getBit(data, 7);
        int result = data << 1;

        addressSpace.writeByte(registers.getHL(), result);

        registers.getFlags().setC(bit7);
        registers.getFlags().setZ(result == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}