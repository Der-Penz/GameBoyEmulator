package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic.right;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register E right
 */
@SuppressWarnings("unused")
public class SRAEInstruction extends BitOpCode {

    public SRAEInstruction() {
        super(0x2B, "SRA E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SRA", DataType.d8);
        registers.setE(aluOperation.apply(registers.getFlags(), registers.getE()));

        return cycles;
    }
}