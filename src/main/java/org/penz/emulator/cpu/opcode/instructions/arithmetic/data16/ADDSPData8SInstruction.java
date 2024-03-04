package org.penz.emulator.cpu.opcode.instructions.arithmetic.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Add signed 8 bit immediate to SP
 */
@SuppressWarnings("unused")
public class ADDSPData8SInstruction extends OpCode {

    public ADDSPData8SInstruction() {
        super(0xE8, "ADD SP, r8", 16, new DataType[]{DataType.r8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADD", DataType.d16, DataType.r8);
        registers.setSP(aluOperation.apply(registers.getFlags(), registers.getSP(), args[0]));
        return cycles;
    }
}
