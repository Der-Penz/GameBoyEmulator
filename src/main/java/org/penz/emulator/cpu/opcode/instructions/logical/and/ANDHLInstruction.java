package org.penz.emulator.cpu.opcode.instructions.logical.and;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * AND accumulator with the address pointed to by HL
 */
public class ANDHLInstruction extends OpCode {

    public ANDHLInstruction() {
        super(0xA6, "AND (HL)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {

        var aluOperation = alu.getOperation("AND", DataType.d8, DataType.d8);

        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), addressSpace.readByte(registers.getHL())));

        return cycles;
    }
}