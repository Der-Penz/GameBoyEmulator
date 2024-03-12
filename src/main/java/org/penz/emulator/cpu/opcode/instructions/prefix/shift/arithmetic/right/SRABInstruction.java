package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic.right;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register B right
 */
@SuppressWarnings("unused")
public class SRABInstruction extends BitOpCode {

    public SRABInstruction() {
        super(0x28, "SRA B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SRA", DataType.d8);
        registers.setB(aluOperation.apply(registers.getFlags(), registers.getB()));

        return cycles;
    }
}