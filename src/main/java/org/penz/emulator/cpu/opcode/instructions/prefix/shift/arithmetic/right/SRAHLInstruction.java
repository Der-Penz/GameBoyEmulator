package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic.right;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
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

        var aluOperation = alu.getOperation("SRA", DataType.d8);
        int result = aluOperation.apply(registers.getFlags(), data);

        addressSpace.writeByte(registers.getHL(), result);

        return cycles;
    }
}