package org.penz.emulator.cpu.opcode.instructions.arithmetic.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Add register DE to HL
 */
@SuppressWarnings("unused")
public class ADDHLDEInstruction extends OpCode {

    public ADDHLDEInstruction() {
        super(0x19, "ADD HL, DE", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADD", DataType.d16, DataType.d16);
        registers.setHL(aluOperation.apply(registers.getFlags(), registers.getHL(), registers.getDE()));
        return cycles;
    }
}
