package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
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

        var aluOperation = alu.getOperation("SRL", DataType.d8);
        int result = aluOperation.apply(registers.getFlags(), data);

        addressSpace.writeByte(registers.getHL(), result);

        return cycles;
    }
}