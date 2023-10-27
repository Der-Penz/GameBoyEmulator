package org.penz.emulator.cpu.opcode.instructions.load.stack;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value of HL into SP
 */
@SuppressWarnings("unused")
public class LDHLSPData8SInstruction extends OpCode {

    public LDHLSPData8SInstruction() {
        super(0xF8, "LD HL, SP+r8", 12, new DataType[]{DataType.r8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADD", DataType.d16, DataType.r8);
        registers.setHL(aluOperation.apply(registers.getFlags(), registers.getSP(), args[0]));
        return cycles;
    }
}
