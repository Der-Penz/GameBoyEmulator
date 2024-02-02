package org.penz.emulator.cpu.opcode.instructions.logical.xor;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * XOR A with 8 bit immediate data
 */
@SuppressWarnings("unused")
public class XORData8Instruction extends OpCode {

    public XORData8Instruction() {
        super(0xEE, "XOR A", 8, new DataType[]{DataType.d8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("XOR", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), args[0]));

        return cycles;
    }
}