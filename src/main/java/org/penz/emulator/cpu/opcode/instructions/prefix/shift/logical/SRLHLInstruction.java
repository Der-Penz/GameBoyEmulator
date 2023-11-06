package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Logically shift data pointed to by register HL left
 */
@SuppressWarnings("unused")
public class SRLHLInstruction extends BitOpCode {

    public SRLHLInstruction() {
        super(0x3E, "SRL (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int data = addressSpace.readByte(registers.getHL());
        boolean bit0 = BitUtil.getBit(data, 0);
        int result = data >> 1;

        addressSpace.writeByte(registers.getHL(), result);

        registers.getFlags().setC(bit0);
        registers.getFlags().setZ(result == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        return cycles;
    }
}