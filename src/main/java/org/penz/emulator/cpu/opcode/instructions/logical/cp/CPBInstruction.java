package org.penz.emulator.cpu.opcode.instructions.logical.cp;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Compare register B with register A
 */
@SuppressWarnings("unused")
public class CPBInstruction extends OpCode {

    public CPBInstruction() {
        super(0xB8, "CP B", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("CP", DataType.d8, DataType.d8);
        aluOperation.apply(registers.getFlags(), registers.getA(), registers.getB());
        return cycles;
    }
}
