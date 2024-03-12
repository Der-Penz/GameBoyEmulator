package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic.left;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register   B left
 */
@SuppressWarnings("unused")
public class SLABInstruction extends BitOpCode {

    public SLABInstruction() {
        super(0x20, "SLA B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SLA", DataType.d8);
        registers.setB(aluOperation.apply(registers.getFlags(), registers.getB()));

        return cycles;
    }
}