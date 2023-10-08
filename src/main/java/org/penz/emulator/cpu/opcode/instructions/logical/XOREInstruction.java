package org.penz.emulator.cpu.opcode.instructions.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * XOR accumulator with register E
 */
public class XOREInstruction extends OpCode {

    public XOREInstruction() {
        super(0xAB, "XOR B", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("XOR", DataType.d8, DataType.d8);

        registers.setE(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getE()));

        return cycles;
    }
}

