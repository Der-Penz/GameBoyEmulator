package org.penz.emulator.cpu.opcode.instructions.logical.or;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * OR accumulator with register the address HL points to
 */
public class ORHLInstruction extends OpCode {

    public ORHLInstruction() {
        super(0xB6, "OR (HL)", 8);
    }
    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {

        var aluOperation = alu.getOperation("OR", DataType.d8, DataType.d8);

        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), addressSpace.readByte(registers.getHL())));

        return cycles;
    }
}