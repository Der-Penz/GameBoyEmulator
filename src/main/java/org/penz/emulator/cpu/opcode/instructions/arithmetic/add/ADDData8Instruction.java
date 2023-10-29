package org.penz.emulator.cpu.opcode.instructions.arithmetic.add;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Add register A and 8 bit immediate data
 */
@SuppressWarnings("unused")
public class ADDData8Instruction extends OpCode {

    public ADDData8Instruction() {
        super(0xC6, "ADD d8", 8, new DataType[]{DataType.d8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADD", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), args[0]));
        return cycles;
    }
}
