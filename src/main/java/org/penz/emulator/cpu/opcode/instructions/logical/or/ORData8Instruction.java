package org.penz.emulator.cpu.opcode.instructions.logical.or;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * OR A with 8 bit immediate data
 */
@SuppressWarnings("unused")
public class ORData8Instruction extends OpCode {

    public ORData8Instruction() {
        super(0xF6, "OR d8", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("OR", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), args[0]));

        return cycles;
    }
}