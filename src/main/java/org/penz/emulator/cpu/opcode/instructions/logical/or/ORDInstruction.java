package org.penz.emulator.cpu.opcode.instructions.logical.or;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * OR accumulator with register D
 */
@SuppressWarnings("unused")
public class ORDInstruction extends OpCode {

    public ORDInstruction() {
        super(0xB2, "OR D", 4);
    }
    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {

        var aluOperation = alu.getOperation("OR", DataType.d8, DataType.d8);

        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getD()));

        return cycles;
    }
}