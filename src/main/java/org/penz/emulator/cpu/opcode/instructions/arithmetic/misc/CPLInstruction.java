package org.penz.emulator.cpu.opcode.instructions.arithmetic.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Complement Accumulator
 */
@SuppressWarnings("unused")
public class CPLInstruction extends OpCode {
    public CPLInstruction() {
        super(0x2F, "CPL", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("CPL", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        return cycles;
    }
}
