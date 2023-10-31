package org.penz.emulator.cpu.opcode.instructions.logical.or;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * OR accumulator with register E
 */
@SuppressWarnings("unused")
public class OREInstruction extends OpCode {

    public OREInstruction() {
        super(0xB3, "OR E", 4);
    }
    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {

        var aluOperation = alu.getOperation("OR", DataType.d8, DataType.d8);

        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getE()));

        return cycles;
    }
}