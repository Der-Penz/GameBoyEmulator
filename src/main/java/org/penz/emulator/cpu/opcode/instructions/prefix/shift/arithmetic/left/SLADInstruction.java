package org.penz.emulator.cpu.opcode.instructions.prefix.shift.arithmetic.left;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Arithmetically shift the register   D left
 */
@SuppressWarnings("unused")
public class SLADInstruction extends BitOpCode {

    public SLADInstruction() {
        super(0x22, "SLA D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SLA", DataType.d8);
        registers.setD(aluOperation.apply(registers.getFlags(), registers.getD()));

        return cycles;
    }
}