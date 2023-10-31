package org.penz.emulator.cpu.opcode.instructions.logical.and;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * AND A with 8 bit immediate data
 */
@SuppressWarnings("unused")
public class ANDData8Instruction extends OpCode {

    public ANDData8Instruction() {
        super(0xE6, "AND d8", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("AND", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), args[0]));

        return cycles;
    }
}
