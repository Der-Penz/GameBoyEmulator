package org.penz.emulator.cpu.opcode.instructions.arithmetic.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decimal Adjust Accumulator after addition or subtraction
 */
@SuppressWarnings("unused")
public class DAAInstruction extends OpCode {
    public DAAInstruction() {
        super(0x27, "DAA", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("DAA", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        return cycles;
    }
}
