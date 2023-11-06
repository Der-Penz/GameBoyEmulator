package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register HL
 */
@SuppressWarnings("unused")
public class SWAPHLInstruction extends BitOpCode {

    public SWAPHLInstruction() {
        super(0x36, "SWAP (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int data = addressSpace.readByte(registers.getHL());
        int highNibble = data & 0xF0;
        int lowNibble = data & 0x0F;
        int result = (lowNibble << 4) | (highNibble >> 4);

        addressSpace.writeByte(registers.getHL(), result);
        registers.getFlags().setZ(result == 0);
        registers.getFlags().setN(false);
        registers.getFlags().setH(false);
        registers.getFlags().setC(false);
        return cycles;
    }
}