package org.penz.emulator.cpu.opcode.instructions.logical.and;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * AND accumulator with register D
 */
public class ANDDInstruction extends OpCode {

    public ANDDInstruction() {
        super(0xA2, "AND D", 4);
    }
    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {

        var aluOperation = alu.getOperation("AND", DataType.d8, DataType.d8);

        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getD()));

        return cycles;
    }
}