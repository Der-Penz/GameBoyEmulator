package org.penz.emulator.cpu.opcode.instructions.logical.cp;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Compare register A with 8 bit immediate data
 */
@SuppressWarnings("unused")
public class CPData8Instruction extends OpCode {

    public CPData8Instruction() {
        super(0xFE, "CP A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("CP", DataType.d8, DataType.d8);
        aluOperation.apply(registers.getFlags(), registers.getA(), args[0]);

        return cycles;
    }
}
