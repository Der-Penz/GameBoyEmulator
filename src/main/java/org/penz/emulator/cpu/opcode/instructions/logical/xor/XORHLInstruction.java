package org.penz.emulator.cpu.opcode.instructions.logical.xor;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * XOR accumulator with value of the address the register HL points to
 */
@SuppressWarnings("unused")
public class XORHLInstruction extends OpCode {

    public XORHLInstruction() {
        super(0xAE, "XOR (HL)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("XOR", DataType.d8, DataType.d8);

        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), addressSpace.readByte(registers.getHL())));

        return cycles;
    }
}