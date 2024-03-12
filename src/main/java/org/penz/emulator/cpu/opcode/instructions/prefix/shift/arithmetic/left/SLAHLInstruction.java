package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic.left;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
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

        var aluOperation = alu.getOperation("SLA", DataType.d8);
        int result = aluOperation.apply(registers.getFlags(), registers.getD());

        addressSpace.writeByte(registers.getHL(), result);

        return cycles;
    }
}