package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
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

        var aluOperation = alu.getOperation("SWAP", DataType.d8);
        int result = aluOperation.apply(registers.getFlags(), data);

        addressSpace.writeByte(registers.getHL(), result);

        return cycles;
    }
}