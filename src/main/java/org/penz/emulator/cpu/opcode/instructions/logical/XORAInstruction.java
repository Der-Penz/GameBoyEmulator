package org.penz.emulator.cpu.opcode.instructions.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * XOR accumulator with register A
 * basically just sets A to 0 due to XORing the same value with itself always results in 0
 */
public class XORAInstruction extends OpCode {

    public XORAInstruction() {
        super(0xAF, "XOR A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("XOR", DataType.d8, DataType.d8);

        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getA()));

        return cycles;
    }
}

