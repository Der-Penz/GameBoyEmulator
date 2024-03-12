package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.right;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate data located where HL points to right through Carry flag.
 */
@SuppressWarnings("unused")
public class RRHLInstruction extends BitOpCode {

    public RRHLInstruction() {
        super(0x1E, "RR (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        int data = addressSpace.readByte(registers.getHL());

        var aluOperation = alu.getOperation("RR", DataType.d8);
        int result = aluOperation.apply(registers.getFlags(), data);

        addressSpace.writeByte(registers.getHL(), result);

        return cycles;
    }
}