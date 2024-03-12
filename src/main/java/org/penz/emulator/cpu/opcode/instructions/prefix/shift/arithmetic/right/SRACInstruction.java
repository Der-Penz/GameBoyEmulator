package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic.right;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register C right
 */
@SuppressWarnings("unused")
public class SRACInstruction extends BitOpCode {

    public SRACInstruction() {
        super(0x29, "SRA C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SRA", DataType.d8);
        registers.setC(aluOperation.apply(registers.getFlags(), registers.getC()));

        return cycles;
    }
}