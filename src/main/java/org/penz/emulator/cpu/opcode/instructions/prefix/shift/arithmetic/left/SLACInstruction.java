package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic.left;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register   C left
 */
@SuppressWarnings("unused")
public class SLACInstruction extends BitOpCode {

    public SLACInstruction() {
        super(0x21, "SLA C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SLA", DataType.d8);
        registers.setC(aluOperation.apply(registers.getFlags(), registers.getC()));

        return cycles;
    }
}