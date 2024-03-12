package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.left;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
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

        var aluOperation = alu.getOperation("RL", DataType.d8);
        int result = aluOperation.apply(registers.getFlags(), data);

        addressSpace.writeByte(registers.getHL(), result);

        return cycles;
    }
}