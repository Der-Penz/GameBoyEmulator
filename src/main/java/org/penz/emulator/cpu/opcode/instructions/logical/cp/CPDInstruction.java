package org.penz.emulator.cpu.opcode.instructions.logical.cp;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Compare register D with register A
 */
@SuppressWarnings("unused")
public class CPDInstruction extends OpCode {

    public CPDInstruction() {
        super(0xBA, "CP D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("CP", DataType.d8, DataType.d8);
        aluOperation.apply(registers.getFlags(), registers.getA(), registers.getD());
        return cycles;
    }
}
