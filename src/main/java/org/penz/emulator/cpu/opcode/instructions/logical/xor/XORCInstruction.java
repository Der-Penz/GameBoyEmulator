package org.penz.emulator.cpu.opcode.instructions.logical.xor;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * XOR accumulator with register C
 */
@SuppressWarnings("unused")
public class XORCInstruction extends OpCode {

    public XORCInstruction() {
        super(0xA9, "XOR C", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("XOR", DataType.d8, DataType.d8);

        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getC()));

        return cycles;
    }
}

