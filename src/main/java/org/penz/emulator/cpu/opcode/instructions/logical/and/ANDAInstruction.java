package org.penz.emulator.cpu.opcode.instructions.logical.and;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * AND accumulator with register A
 */
public class ANDAInstruction extends OpCode {

    public ANDAInstruction() {
        super(0xA7, "AND A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {

        var aluOperation = alu.getOperation("AND", DataType.d8, DataType.d8);

        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getA()));

        return cycles;
    }
}
